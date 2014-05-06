package com.mechanitis.mongodb.gettingstarted;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ConnectionTest {
    @Test
    public void shouldConnectToTheMongoInstance() throws Exception {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        // Alternatively, this will initialise one with the default host and port
        // MongoClient mongoClient = new MongoClient();

        DB database = mongoClient.getDB("TheDatabaseName");
        DBCollection collection = database.getCollection("TheCollectionName");

        assertThat(mongoClient, is(notNullValue()));
        assertThat(database, is(notNullValue()));
        assertThat(collection, is(notNullValue()));
    }
}
