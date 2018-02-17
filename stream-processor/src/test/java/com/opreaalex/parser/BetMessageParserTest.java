package com.opreaalex.parser;

import com.opreaalex.domain.BetMessage;
import com.opreaalex.domain.BetMessageOperation;
import com.opreaalex.domain.BetMessageType;
import com.opreaalex.parser.exception.StreamLineParserException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.time.Instant;

public class BetMessageParserTest {

    private BetMessageParser parser = new BetMessageParser();

    @Test
    public void testParseEventSuccess() throws StreamLineParserException {
        final String line = "|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|1|";

        final BetMessage expected = new BetMessage();
        expected.setId(new BigInteger("2054"));
        expected.setOperation(BetMessageOperation.CREATE);
        expected.setType(BetMessageType.EVENT);
        expected.setTimestamp(Instant.ofEpochMilli(1497359166352L));

        expected.setEventId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expected.setCategory("Football");
        expected.setSubCategory("Sky Bet League Two");
        expected.setStartTime(Instant.ofEpochMilli(1497359216693L));

        expected.setName("\\|Accrington\\| vs \\|Cambridge\\|");
        expected.setDisplayed(false);
        expected.setSuspended(true);

        Assert.assertEquals(expected, parser.parseLine(line));
    }

    @Test
    public void testParseMarketSuccess() throws StreamLineParserException {
        final String line = "|2054|update|market|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c3|\\|Accrington\\| vs \\|Cambridge\\||1|0|";

        final BetMessage expected = new BetMessage();
        expected.setId(new BigInteger("2054"));
        expected.setOperation(BetMessageOperation.UPDATE);
        expected.setType(BetMessageType.MARKET);
        expected.setTimestamp(Instant.ofEpochMilli(1497359166352L));

        expected.setEventId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expected.setMarketId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c3");

        expected.setName("\\|Accrington\\| vs \\|Cambridge\\|");
        expected.setDisplayed(true);
        expected.setSuspended(false);

        Assert.assertEquals(expected, parser.parseLine(line));
    }

    @Test
    public void testParseOutcomeSuccess() throws StreamLineParserException {
        final String line = "|2054|create|outcome|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c3|\\|Accrington\\| vs \\|Cambridge\\||$12.00|0|1|";

        final BetMessage expected = new BetMessage();
        expected.setId(new BigInteger("2054"));
        expected.setOperation(BetMessageOperation.CREATE);
        expected.setType(BetMessageType.OUTCOME);
        expected.setTimestamp(Instant.ofEpochMilli(1497359166352L));

        expected.setMarketId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expected.setOutcomeId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c3");
        expected.setPrice("$12.00");

        expected.setName("\\|Accrington\\| vs \\|Cambridge\\|");
        expected.setDisplayed(false);
        expected.setSuspended(true);

        Assert.assertEquals(expected, parser.parseLine(line));
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBadFormat() throws StreamLineParserException {
        parser.parseLine("|just|another...format-something-over-9000");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBadId() throws StreamLineParserException {
        parser.parseLine("|abc|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|1|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBadOperation() throws StreamLineParserException {
        parser.parseLine("|2054|put|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|1|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBadType() throws StreamLineParserException {
        parser.parseLine("|2054|create|bob|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|1|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBadTimestamp() throws StreamLineParserException {
        parser.parseLine("|2054|create|event|something|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|1|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBadDisplayed() throws StreamLineParserException{
        parser.parseLine("|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|abc|1|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBadSuspended() throws StreamLineParserException{
        parser.parseLine("|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|abc|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseEventBadStartTime() throws StreamLineParserException {
        parser.parseLine("|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||other_thing|0|1|");
    }
}
