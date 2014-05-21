package com.mechanitis.mongodb.gettingstarted;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
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
        DB database = null;

        // Then
        assertThat(database, is(notNullValue()));
    }

    @Test
    public void shouldGetACollectionFromTheDatabase() throws Exception {
        // Given
        // TODO any setup

        // When
        // TODO get collection
        DBCollection collection = null;

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
        mongoClient.getDB("SomeDatabase").getCollection("coll").insert(new BasicDBObject("field", "value"));
    }

}
