package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Exercise13UpsertTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Test
    public void shouldOnlyInsertDocumentIfItDidNotExistWhenUpsertIsTrue() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(
                27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890),
                                    asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        // new person not in the database yet
        Person claire = new Person("claire", "Claire", new Address("1", "Town", 836558493), Collections
                .<Integer>emptyList());

        // When
        // TODO create query to find Claire by ID
        Document findClaire = null;
        // TODO Perform a replacement with this new person to show it does NOT get added to the database
        UpdateResult resultOfUpdate = null;

        // Then
        assertThat(resultOfUpdate.getModifiedCount(), is(0L));
        // without upsert this should not have been inserted
        assertThat(collection.count(findClaire), is(0L));


        // When
        // TODO Perform an update with this new person to show it DOES get added to the database
        UpdateResult updateResult = null;

        // Then
        Document newClaireDocument = collection.find(findClaire).first();
        // all values should have been updated to the new object values
        assertThat(newClaireDocument.getString("_id"), is(claire.getId()));
        assertThat(collection.count(findClaire), is(1L));
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
