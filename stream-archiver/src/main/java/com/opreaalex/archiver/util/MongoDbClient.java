package com.opreaalex.archiver.util;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import java.util.Map;

public class MongoDbClient {

    private static final String HOST = "localhost";
    private static final int PORT = 27017;

    private static final String DB_NAME = "testing";
    private static final String COLLECITON_NAME = "bets";

    private final MongoClient client;

    private final MongoDatabase database;

    private final MongoCollection collection;

    public MongoDbClient() {
        client = new MongoClient(HOST, PORT);
        database = client.getDatabase(DB_NAME);
        collection = database.getCollection(COLLECITON_NAME);
    }

    public <T> void create(final T entry, final CollectionMapper<T> mapper) {
        final BasicDBObject document = new BasicDBObject();
        document.putAll(mapper.mapEntry(entry));
        collection.insertOne(document);
    }

    public interface CollectionMapper <T> {
        Map<String, Object> mapEntry(T entry);
    }
}
