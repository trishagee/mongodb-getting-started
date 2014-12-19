package com.mechanitis.mongodb.gettingstarted;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Exercise9IndexTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Test
    public void shouldCreateAnAscendingIndex() {
        // given
        collection.insertOne(new Document("fieldToIndex", "Bob"));
        
        // when
        // TODO: added the index to the collection

        // then
        Document indexKey = (Document) collection.getIndexes().get(1).get("key");
        assertTrue(indexKey.keySet().contains("fieldToIndex"));
        assertThat(indexKey.getInteger("fieldToIndex"), is(1));
        assertThat(collection.getIndexes().get(1).getString("name"), is("fieldToIndex_1"));
    }

    @Before
    public void setUp() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDatabase("Examples");
        collection = database.getCollection("people");
    }

    @After
    public void tearDown() {
        database.dropDatabase();
    }
}
