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
        MongoClient mongoClient = new MongoClient();

        // When
        MongoDatabase database = mongoClient.getDatabase("TheDatabaseName");

        // Then
        assertThat(database, is(notNullValue()));
    }

    @Test
    public void shouldGetACollectionFromTheDatabase() throws Exception {
        // Given
        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("TheDatabaseName");

        // When
        MongoCollection<Document> collection = database.getCollection("TheCollectionName");

        // Then
        assertThat(collection, is(notNullValue()));
    }

    @Test(expected = Exception.class)
    public void shouldNotBeAbleToUseMongoClientAfterItHasBeenClosed() throws UnknownHostException {
        // Given
        MongoClient mongoClient = new MongoClient();

        // When
        mongoClient.close();

        // Then
        mongoClient.getDatabase("SomeDatabase").getCollection("coll").insertOne(new Document("field", "value"));
    }

}
