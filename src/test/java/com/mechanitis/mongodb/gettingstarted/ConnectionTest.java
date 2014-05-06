package com.mechanitis.mongodb.gettingstarted;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ConnectionTest {
    @Test
    public void shouldCreateANewMongoClientConnectedToLocalhost() throws Exception {
        // When
        // TODO: get/create the MongoClient
        MongoClient mongoClient = null;

        // Then
        assertThat(mongoClient, is(notNullValue()));
    }

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
}
