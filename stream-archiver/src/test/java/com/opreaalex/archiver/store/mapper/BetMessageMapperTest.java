package com.opreaalex.archiver.store.mapper;

import com.opreaalex.common.domain.BetMessage;
import com.opreaalex.common.domain.BetMessageOperation;
import com.opreaalex.common.domain.BetMessageType;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class BetMessageMapperTest {

    private BetMessageMapper mapper = new BetMessageMapper();

    @Test
    public void testMapEntryEvent() {
        final BetMessage message = new BetMessage();
        message.setId(new BigInteger("123"));
        message.setOperation(BetMessageOperation.CREATE);
        message.setType(BetMessageType.EVENT);
        message.setTimestamp(Instant.ofEpochMilli(12345678901L));
        message.setEventId("1234567890");
        message.setCategory("War");
        message.setSubCategory("Greek Mainland");
        message.setStartTime(Instant.ofEpochMilli(23456789012L));
        message.setName("Sparta vs Persia");
        message.setDisplayed(true);
        message.setSuspended(false);

        final Map<String, Object> expected = new HashMap<>();
        expected.put("id", 123);
        expected.put("operation", "create");
        expected.put("type", "event");
        expected.put("timestamp", 12345678901L);
        expected.put("eventId", "1234567890");
        expected.put("category", "War");
        expected.put("subCategory", "Greek Mainland");
        expected.put("startTime", 23456789012L);
        expected.put("name", "Sparta vs Persia");
        expected.put("displayed", true);
        expected.put("suspended", false);

        Assert.assertEquals(expected, mapper.mapEntry(message));
    }

    @Test
    public void testMapEntryMarket() {
        final BetMessage message = new BetMessage();
        message.setId(new BigInteger("123"));
        message.setOperation(BetMessageOperation.UPDATE);
        message.setType(BetMessageType.MARKET);
        message.setTimestamp(Instant.ofEpochMilli(12345678901L));
        message.setEventId("1234567890");
        message.setMarketId("2345678901");
        message.setName("Sparta vs Persia");
        message.setDisplayed(false);
        message.setSuspended(true);

        final Map<String, Object> expected = new HashMap<>();
        expected.put("id", 123);
        expected.put("operation", "update");
        expected.put("type", "market");
        expected.put("timestamp", 12345678901L);
        expected.put("eventId", "1234567890");
        expected.put("marketId", "2345678901");
        expected.put("name", "Sparta vs Persia");
        expected.put("displayed", false);
        expected.put("suspended", true);

        Assert.assertEquals(expected, mapper.mapEntry(message));
    }

    @Test
    public void testMapEntryOutcome() {
        final BetMessage message = new BetMessage();
        message.setId(new BigInteger("123"));
        message.setOperation(BetMessageOperation.CREATE);
        message.setType(BetMessageType.OUTCOME);
        message.setTimestamp(Instant.ofEpochMilli(12345678901L));
        message.setMarketId("2345678901");
        message.setOutcomeId("3456789012");
        message.setPrice("$9001.00");
        message.setName("Sparta vs Persia");
        message.setDisplayed(false);
        message.setSuspended(true);

        final Map<String, Object> expected = new HashMap<>();
        expected.put("id", 123);
        expected.put("operation", "create");
        expected.put("type", "outcome");
        expected.put("timestamp", 12345678901L);
        expected.put("marketId", "2345678901");
        expected.put("outcomeId", "3456789012");
        expected.put("price", "$9001.00");
        expected.put("name", "Sparta vs Persia");
        expected.put("displayed", false);
        expected.put("suspended", true);

        Assert.assertEquals(expected, mapper.mapEntry(message));
    }
}
