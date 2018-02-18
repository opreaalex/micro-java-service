package com.opreaalex.processor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opreaalex.processor.domain.BetMessage;
import com.opreaalex.processor.domain.BetMessageOperation;
import com.opreaalex.processor.domain.BetMessageType;
import com.opreaalex.processor.parser.StreamLineParser;
import com.opreaalex.processor.parser.exception.StreamLineParserException;
import com.opreaalex.processor.store.BetStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigInteger;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RunWith(MockitoJUnitRunner.class)
public class BetServiceTest {

    @Mock
    private StreamLineParser parser;

    @Mock
    private BetStore store;

    @InjectMocks
    private BetService service;

    @Test
    public void testOnNewLineEvent()
            throws StreamLineParserException, JsonProcessingException {
        final BetMessage message = new BetMessage();
        message.setId(new BigInteger("2054"));
        message.setOperation(BetMessageOperation.CREATE);
        message.setType(BetMessageType.EVENT);
        message.setTimestamp(Instant.ofEpochMilli(1497359166352L));
        message.setEventId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        message.setCategory("Football");
        message.setSubCategory("Sky Bet League Two");
        message.setStartTime(Instant.ofEpochMilli(1497359216693L));
        message.setName("\\|Accrington\\| vs \\|Cambridge\\|");
        message.setDisplayed(false);
        message.setSuspended(true);

        Mockito.when(parser.parseLine(Mockito.anyString())).thenReturn(message);

        service.onNewLine("Should not matter");

        final Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put("id", 2054);
        expectedMap.put("operation", "create");
        expectedMap.put("type", "event");
        expectedMap.put("timestamp", 1497359166352L);
        expectedMap.put("eventId", "ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expectedMap.put("category", "Football");
        expectedMap.put("subCategory", "Sky Bet League Two");
        expectedMap.put("name", "\\|Accrington\\| vs \\|Cambridge\\|");
        expectedMap.put("startTime", 1497359216693L);
        expectedMap.put("displayed", false);
        expectedMap.put("suspended", true);

        final String expected = new ObjectMapper()
                .writeValueAsString(expectedMap);

        Mockito.verify(store).storeJson(expected);
    }

    @Test
    public void testOnNewLineMarket()
            throws StreamLineParserException, JsonProcessingException {
        final BetMessage message = new BetMessage();
        message.setId(new BigInteger("2054"));
        message.setOperation(BetMessageOperation.UPDATE);
        message.setType(BetMessageType.MARKET);
        message.setTimestamp(Instant.ofEpochMilli(1497359166352L));
        message.setEventId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        message.setMarketId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c3");
        message.setName("\\|Accrington\\| vs \\|Cambridge\\|");
        message.setDisplayed(false);
        message.setSuspended(true);

        Mockito.when(parser.parseLine(Mockito.anyString())).thenReturn(message);

        service.onNewLine("Should not matter");

        final Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put("id", 2054);
        expectedMap.put("operation", "update");
        expectedMap.put("type", "market");
        expectedMap.put("timestamp", 1497359166352L);
        expectedMap.put("eventId", "ee4d2439-e1c5-4cb7-98ad-9879b2fd84c2");
        expectedMap.put("marketId", "ee4d2439-e1c5-4cb7-98ad-9879b2fd84c3");
        expectedMap.put("name", "\\|Accrington\\| vs \\|Cambridge\\|");
        expectedMap.put("displayed", false);
        expectedMap.put("suspended", true);

        final String expected = new ObjectMapper()
                .writeValueAsString(expectedMap);

        Mockito.verify(store).storeJson(expected);
    }

    @Test
    public void testOnNewLineOutcome()
            throws StreamLineParserException, JsonProcessingException {
        final BetMessage message = new BetMessage();
        message.setId(new BigInteger("2054"));
        message.setOperation(BetMessageOperation.CREATE);
        message.setType(BetMessageType.OUTCOME);
        message.setTimestamp(Instant.ofEpochMilli(1497359166352L));
        message.setMarketId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c3");
        message.setOutcomeId("ee4d2439-e1c5-4cb7-98ad-9879b2fd84c4");
        message.setName("\\|Accrington\\| vs \\|Cambridge\\|");
        message.setPrice("$13.00");
        message.setDisplayed(true);
        message.setSuspended(false);

        Mockito.when(parser.parseLine(Mockito.anyString())).thenReturn(message);

        service.onNewLine("Should not matter");

        final Map<String, Object> expectedMap = new LinkedHashMap<>();
        expectedMap.put("id", 2054);
        expectedMap.put("operation", "create");
        expectedMap.put("type", "outcome");
        expectedMap.put("timestamp", 1497359166352L);
        expectedMap.put("marketId", "ee4d2439-e1c5-4cb7-98ad-9879b2fd84c3");
        expectedMap.put("outcomeId", "ee4d2439-e1c5-4cb7-98ad-9879b2fd84c4");
        expectedMap.put("name", "\\|Accrington\\| vs \\|Cambridge\\|");
        expectedMap.put("price", "$13.00");
        expectedMap.put("displayed", true);
        expectedMap.put("suspended", false);

        final String expected = new ObjectMapper()
                .writeValueAsString(expectedMap);

        Mockito.verify(store).storeJson(expected);
    }
}
