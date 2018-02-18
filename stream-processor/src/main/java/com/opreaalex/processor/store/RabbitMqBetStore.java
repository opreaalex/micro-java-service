package com.opreaalex.processor.store;

import com.opreaalex.processor.util.RabbitMqClient;

public class RabbitMqBetStore implements BetStore {

    private final RabbitMqClient client = new RabbitMqClient(false);

    @Override
    public void storeJson(final String jsonStr) {
        // Simply publish the message to the default exchange
        client.publish(jsonStr.getBytes());
    }
}
