package com.opreaalex.archiver.util;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqClient {

    private static final String MQ_HOST = "localhost";
    private static final String MQ_USER = "rabbitmq";
    private static final String MQ_PASS = "rabbitmq";
    private static final String MQ_VHOST = "/feedme";

    private static final String MQ_QUEUE = "bet-queue";

    private final ConnectionFactory connectionFactory;

    public RabbitMqClient() {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(MQ_HOST);
        connectionFactory.setUsername(MQ_USER);
        connectionFactory.setPassword(MQ_PASS);
        connectionFactory.setVirtualHost(MQ_VHOST);
        connectionFactory.setAutomaticRecoveryEnabled(true);

    }

    public void startConsuming() {
        try {
            final Connection connection = connectionFactory.newConnection();
            final Channel channel = connection.createChannel();
            channel.basicConsume(MQ_QUEUE, new BetConsumer(channel));
        } catch (final IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private class BetConsumer extends DefaultConsumer {


        public BetConsumer(final Channel channel) {
            super(channel);
        }

        @Override
        public void handleDelivery(final String consumerTag,
                                   final Envelope envelope,
                                   final AMQP.BasicProperties properties,
                                   final byte[] body)
                throws IOException {
            final String jsonStr = new String(body, "UTF-8");
            System.out.println(jsonStr);
        }
    }
}
