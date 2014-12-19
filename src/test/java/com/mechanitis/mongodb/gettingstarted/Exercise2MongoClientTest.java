package com.mechanitis.mongodb.gettingstarted;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class Exercise2MongoClientTest {
    @Test
    public void shouldGetADatabaseFromTheMongoClient() throws Exception {
        // Given
        // TODO any setup

        // When
        //TODO get the database from the client
        MongoDatabase database = null;

        // Then
        assertThat(database, is(notNullValue()));
    }

    @Test
    public void shouldGetACollectionFromTheDatabase() throws Exception {
        // Given
        // TODO any setup

        // When
        // TODO get collection
        MongoCollection<Document> collection = null;

        // Then
        assertThat(collection, is(notNullValue()));
    }

    @Test(expected = Exception.class)
    public void shouldNotBeAbleToUseMongoClientAfterItHasBeenClosed() throws UnknownHostException {
        // Given
        MongoClient mongoClient = new MongoClient();

        // When
        // TODO close the mongoClient

        // Then
        mongoClient.getDatabase("SomeDatabase").getCollection("coll").insertOne(new Document("field", "value"));
    }

}
