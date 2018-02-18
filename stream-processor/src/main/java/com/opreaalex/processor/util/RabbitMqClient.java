package com.opreaalex.processor.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class RabbitMqClient {

    private static final String MQ_HOST = "localhost";
    private static final String MQ_USER = "rabbitmq";
    private static final String MQ_PASS = "rabbitmq";
    private static final String MQ_VHOST = "/feedme";

    private static final String MQ_EXCHANGE = "bet-messages";
    private static final String MQ_EXCHANGE_TYPE = "fanout";
    private static final String MQ_QUEUE = "bet-queue";

    private final boolean useAsync;

    private final ExecutorService executorService;

    private final ConnectionFactory connectionFactory;

    public RabbitMqClient(final boolean useAsync) {
        this.useAsync = useAsync;

        // Please don't mess around with this
        // as you might get NPE if you're not careful (in the future)
        executorService = useAsync ?
                Executors.newFixedThreadPool(5) : null;

        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(MQ_HOST);
        connectionFactory.setUsername(MQ_USER);
        connectionFactory.setPassword(MQ_PASS);
        connectionFactory.setVirtualHost(MQ_VHOST);
        connectionFactory.setAutomaticRecoveryEnabled(true);

        setupExchangeAndQueue();
    }

    public void publish(final byte[] payload) {
        if (useAsync) {
            executorService.execute(() -> publishInternal(payload));
        } else {
            publishInternal(payload);
        }
    }

    private void publishInternal(final byte[] payload) {
        try {
            final Connection connection = connectionFactory.newConnection();
            final Channel channel = connection.createChannel();

            channel.basicPublish(MQ_EXCHANGE, "", null, payload);

            channel.close();
            connection.close();
        } catch (final IOException | TimeoutException e) {
            // Should retry after x time
            e.printStackTrace();
        }
    }

    private void setupExchangeAndQueue() {
        try {
            final Connection connection = connectionFactory.newConnection();
            final Channel channel = connection.createChannel();

            channel.exchangeDeclare(MQ_EXCHANGE, MQ_EXCHANGE_TYPE);
            channel.queueDeclare(MQ_QUEUE, false, false, false, null);
            channel.queueBind(MQ_QUEUE, MQ_EXCHANGE, "");

            channel.close();
            connection.close();
        } catch (final IOException | TimeoutException e) {
            // Should retry after x time
            e.printStackTrace();
        }
    }
}
