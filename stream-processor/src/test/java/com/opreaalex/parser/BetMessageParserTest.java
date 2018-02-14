package com.opreaalex.parser;

import com.opreaalex.domain.BaseBetMessage;
import com.opreaalex.domain.BetMessageHeader;
import com.opreaalex.domain.BetMessageOperation;
import com.opreaalex.domain.EventBetMessage;
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

    // Begin BaseBetMessage
    @Test(expected = StreamLineParserException.class)
    public void testParseBaseBetMessageBadFormat() throws StreamLineParserException {
        parser.parseLine("this-is-a-|-bad-format");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBaseBetMessageBadId() throws StreamLineParserException {
        parser.parseLine("|abc|create|event|1497359166352|some-irrelevant-text-for-this-test");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBaseBetMessageBadOperation() throws StreamLineParserException {
        parser.parseLine("|2054|abc|event|1497359166352|some-irrelevant-text-for-this-test");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBaseBetMessageBadType() throws StreamLineParserException {
        parser.parseLine("|2054|create|abc|1497359166352|some-irrelevant-text-for-this-test");
    }

    @Test(expected = StreamLineParserException.class)
    public void testParseBaseBetMessageBadInstant() throws StreamLineParserException {
        parser.parseLine("|2054|create|event|abc|some-irrelevant-text-for-this-test");
    }
    // End BaseBetMessage

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
        expected.setName("Sky Bet League Two|\\|Accrington\\| vs \\|Cambridge\\|");
        expected.setStartInstant(Instant.ofEpochMilli(1497359216693L));
        expected.setDisplayed(false);
        expected.setSuspended(true);

        Assert.assertEquals(expected, parser.parseLine(line));
    }
    // End EventBetMessage
}
