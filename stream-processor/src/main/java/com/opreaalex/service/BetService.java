package com.opreaalex.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.opreaalex.domain.BetMessage;
import com.opreaalex.parser.StreamLineParser;
import com.opreaalex.parser.exception.StreamLineParserException;
import com.opreaalex.reader.StreamReaderHandler;
import com.opreaalex.service.serializer.BetMessageSerializer;
import com.opreaalex.store.BetStore;

public class BetService implements StreamReaderHandler {

    private StreamLineParser parser;

    private BetStore store;

    private ObjectMapper objectMapper;

    public BetService() {
        // Setup the objectMapper
        objectMapper = new ObjectMapper();

        final SimpleModule module = new SimpleModule();
        module.addSerializer(BetMessage.class, new BetMessageSerializer());

        objectMapper.registerModule(module);
    }

    public void onNewLine(final String line) {
        try {
            final BetMessage msg = (BetMessage) parser.parseLine(line);
            final String msgJsonStr = createBetMessageJson(msg);
            store.storeJson(msgJsonStr);
        } catch (StreamLineParserException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private String createBetMessageJson(final BetMessage msg)
            throws JsonProcessingException {
        return objectMapper.writeValueAsString(msg);
    }

    public void setParser(StreamLineParser parser) {
        this.parser = parser;
    }

    public void setStore(BetStore store) {
        this.store = store;
    }
}
