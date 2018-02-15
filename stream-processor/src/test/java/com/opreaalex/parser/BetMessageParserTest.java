package com.opreaalex.parser;

import com.opreaalex.domain.*;
import com.opreaalex.parser.exception.StreamLineParserException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.time.Instant;

public class BetMessageParserTest {

    private BetMessageParser parser;

    @Before
    public void setUp() {
        parser = new BetMessageParser();
    }

    // Begin EventBetMessage
    @Test
    public void testParseEventBetMessageSuccess() throws StreamLineParserException {
        final String line = "|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|1|";

        final BetMessageHeader expectedHeader = new BetMessageHeader();
        expectedHeader.setId(new BigInteger("2054"));
        expectedHeader.setOperation(BetMessageOperation.CREATE);
        expectedHeader.setInstant(Instant.ofEpochMilli(1497359166352L));

        final EventBetMessage expected = new EventBetMessage();
        expected.setHeader(expectedHeader);
        expected.setEventId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expected.setCategory("Football");
        expected.setSubCategory("Sky Bet League Two");
        expected.setName("\\|Accrington\\| vs \\|Cambridge\\|");
        expected.setStartInstant(Instant.ofEpochMilli(1497359216693L));
        expected.setDisplayed(false);
        expected.setSuspended(true);

        Assert.assertEquals(expected, parser.parseLine(line));
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseEventBetMessageBadLength() throws StreamLineParserException {
        parser.parseLine("|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseEventBetMessageBadStartInstant() throws StreamLineParserException {
        parser.parseLine("|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||abc|0|1|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseEventBetMessageBadDisplayed() throws StreamLineParserException {
        parser.parseLine("|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|abc|1|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseEventBetMessageBadSuspended() throws StreamLineParserException {
        parser.parseLine("|2054|create|event|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|Football|Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\||1497359216693|0|abc|");
    }
    // End EventBetMessage

    // Begin MarketBetMessage
    @Test
    public void testParseMarketBetMessageSuccess() throws StreamLineParserException {
        final String line = "|2054|update|market|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|\\|Accrington\\| vs \\|Cambridge\\||0|1|";

        final BetMessageHeader expectedHeader = new BetMessageHeader();
        expectedHeader.setId(new BigInteger("2054"));
        expectedHeader.setOperation(BetMessageOperation.UPDATE);
        expectedHeader.setInstant(Instant.ofEpochMilli(1497359166352L));

        final MarketBetMessage expected = new MarketBetMessage();
        expected.setHeader(expectedHeader);
        expected.setEventId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expected.setMarketId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expected.setName("\\|Accrington\\| vs \\|Cambridge\\|");
        expected.setDisplayed(false);
        expected.setSuspended(true);

        Assert.assertEquals(expected, parser.parseLine(line));
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseMarketBetMessageBadLength() throws StreamLineParserException {
        parser.parseLine("|2054|update|market|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|\\|Accrington\\| vs \\|Cambridge\\||");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseMarketBetMessageBadDisplayed() throws StreamLineParserException {
        parser.parseLine("|2054|update|market|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|\\|Accrington\\| vs \\|Cambridge\\||abc|1|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseMarketBetMessageBadSuspended() throws StreamLineParserException {
        parser.parseLine("|2054|update|market|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|\\|Accrington\\| vs \\|Cambridge\\||0|abc|");
    }
    // End MarketBetMessage

    // Begin OutcomeBetMessage
    @Test
    public void testParseOutcomeBetMessageSuccess() throws StreamLineParserException {
        final String line = "|2054|create|outcome|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|\\|Accrington\\| vs \\|Cambridge\\||$12.00|0|1|";

        final BetMessageHeader expectedHeader = new BetMessageHeader();
        expectedHeader.setId(new BigInteger("2054"));
        expectedHeader.setOperation(BetMessageOperation.CREATE);
        expectedHeader.setInstant(Instant.ofEpochMilli(1497359166352L));

        final OutcomeBetMessage expected = new OutcomeBetMessage();
        expected.setHeader(expectedHeader);
        expected.setMarketId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expected.setOutcomeId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expected.setName("\\|Accrington\\| vs \\|Cambridge\\|");
        expected.setPrice("$12.00");
        expected.setDisplayed(false);
        expected.setSuspended(true);

        Assert.assertEquals(expected, parser.parseLine(line));
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseOutcomeBetMessageBadLength() throws StreamLineParserException {
        parser.parseLine("|2054|create|outcome|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|\\|Accrington\\| vs \\|Cambridge\\||");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseOutcomeBetMessageBadDisplayed() throws StreamLineParserException {
        parser.parseLine("|2054|create|outcome|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|\\|Accrington\\| vs \\|Cambridge\\||$12.00|abc|1|");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseOutcomeBetMessageBadSuspended() throws StreamLineParserException {
        parser.parseLine("|2054|create|outcome|1497359166352|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2|\\|Accrington\\| vs \\|Cambridge\\||$12.00|0|abc|");
    }
    // End OutcomeBetMessage
}
