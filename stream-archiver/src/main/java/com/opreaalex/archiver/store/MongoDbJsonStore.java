package com.opreaalex.archiver.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opreaalex.archiver.store.mapper.BetMessageMapper;
import com.opreaalex.archiver.util.MongoDbClient;
import com.opreaalex.archiver.util.RabbitMqClient;
import com.opreaalex.common.domain.BetMessage;

import java.io.IOException;

public class MongoDbJsonStore implements JsonStore, RabbitMqClient.RabbitMqClientHandler {

    private final MongoDbClient client;

    private final ObjectMapper objectMapper;

    private final BetMessageMapper messageMapper;

    public MongoDbJsonStore() {
        client = new MongoDbClient();
        objectMapper = new ObjectMapper();
        messageMapper = new BetMessageMapper();
    }

    @Override
    public void store(final String jsonStr) {
        try {
	    System.out.println(String.format("Storring msg: %s", jsonStr));
            final BetMessage message = objectMapper.readValue(
                    jsonStr, BetMessage.class);
            client.create(message, messageMapper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleMessage(final String msg) {
        store(msg);
    }
}
