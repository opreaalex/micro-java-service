package com.opreaalex.archiver;

import com.opreaalex.archiver.store.MongoDbJsonStore;
import com.opreaalex.archiver.util.RabbitMqClient;

public class Application {

    public static void main(final String[] args) {
        final RabbitMqClient client = new RabbitMqClient(
                new MongoDbJsonStore());
        client.startConsuming();
    }
}
