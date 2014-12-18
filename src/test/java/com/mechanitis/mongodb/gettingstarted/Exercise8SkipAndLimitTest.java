package com.mechanitis.mongodb.gettingstarted;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Exercise8SkipAndLimitTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Test
    public void shouldReturnDBObjects3to9Of20DBObjectsUsingSkipAndLimit() {
        // Given
        for (int i = 0; i < 20; i++) {
            collection.insertOne(new Document("name", "person" + i).append("someIntValue", i));
        }

        // When
        MongoCursor<Document> results = collection.find()
                                                  .sort(new Document("someIntValue", 1))
                                                  .skip(3)
                                                  .limit(7)
                                                  .iterator();

        // Then
        assertThat(results.next().getInteger("someIntValue"), is(3));
        assertThat(results.next().getInteger("someIntValue"), is(4));
        assertThat(results.next().getInteger("someIntValue"), is(5));
        assertThat(results.next().getInteger("someIntValue"), is(6));
        assertThat(results.next().getInteger("someIntValue"), is(7));
        assertThat(results.next().getInteger("someIntValue"), is(8));
        assertThat(results.next().getInteger("someIntValue"), is(9));
        assertThat(results.hasNext(), is(false));
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
