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

    private final RabbitMqClientHandler handler;

    private final ConnectionFactory connectionFactory;

    public RabbitMqClient(final RabbitMqClientHandler handler) {
        this.handler = handler;
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
            channel.basicConsume(MQ_QUEUE, new BetConsumer(channel, handler));
        } catch (final IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    private class BetConsumer extends DefaultConsumer {

        private final RabbitMqClientHandler handler;

        BetConsumer(final Channel channel,
                    final RabbitMqClientHandler handler) {
            super(channel);
            this.handler = handler;
        }

        @Override
        public void handleDelivery(final String consumerTag,
                                   final Envelope envelope,
                                   final AMQP.BasicProperties properties,
                                   final byte[] body)
                throws IOException {
            handler.handleMessage(new String(body, "UTF-8"));
        }
    }

    public interface RabbitMqClientHandler {
        void handleMessage(String msg);
    }
}
