package com.mechanitis.mongodb.gettingstarted;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class Exercise9IndexTest {
    private DB database;
    private DBCollection collection;

    @Test
    public void shouldCreateAnAscendingIndex() {
        // given
        collection.insert(new BasicDBObject("fieldToIndex", "Bob"));
        
        // when
        // TODO: added the index to the collection

        // then
        DBObject indexKey = (DBObject) collection.getIndexInfo().get(1).get("key");
        assertTrue(indexKey.keySet().contains("fieldToIndex"));
        assertThat((Integer) indexKey.get("fieldToIndex"), is(1));
        assertThat((String) collection.getIndexInfo().get(1).get("name"), is("fieldToIndex_1"));
    }

    @Before
    public void setUp() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("Examples");
        collection = database.getCollection("people");
    }

    @After
    public void tearDown() {
        database.dropDatabase();
    }
}
