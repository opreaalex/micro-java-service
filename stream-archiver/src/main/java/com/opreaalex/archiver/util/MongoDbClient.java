package com.opreaalex.archiver.util;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Map;

public class MongoDbClient {

    private static final String HOST = "localhost";
    private static final int PORT = 27017;

    private static final String DB_NAME = "testing";
    private static final String COLLECITON_NAME = "bets";

    private final MongoClient client;

    private final MongoDatabase database;

    public MongoDbClient() {
        client = new MongoClient(HOST, PORT);
        database = client.getDatabase(DB_NAME);
    }

    public <T> void create(final T entry, CollectionMapper<T> mapper) {
        final Document document = new Document();
        document.putAll(mapper.mapEntry(entry));
        database.getCollection(COLLECITON_NAME).insertOne(document);
    }

    public interface CollectionMapper <T> {
        Map<String, Object> mapEntry(T entry);
    }
}
