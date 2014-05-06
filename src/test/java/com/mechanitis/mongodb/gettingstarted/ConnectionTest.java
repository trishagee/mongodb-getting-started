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
    public void shouldCreateANewMongoClientConnectedToLocalhost() throws Exception {
        // When
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        // Alternatively, this will initialise one with the default host and port
        // MongoClient mongoClient = new MongoClient();

        // Then
        assertThat(mongoClient, is(notNullValue()));
    }

    @Test
    public void shouldGetADatabaseFromTheMongoClient() throws Exception {
        // Given
        MongoClient mongoClient = new MongoClient();

        // When
        DB database = mongoClient.getDB("TheDatabaseName");

        // Then
        assertThat(database, is(notNullValue()));
    }

    @Test
    public void shouldGetACollectionFromTheDatabase() throws Exception {
        // Given
        MongoClient mongoClient = new MongoClient();
        DB database = mongoClient.getDB("TheDatabaseName");

        // When
        DBCollection collection = database.getCollection("TheCollectionName");

        // Then
        assertThat(collection, is(notNullValue()));
    }
}
