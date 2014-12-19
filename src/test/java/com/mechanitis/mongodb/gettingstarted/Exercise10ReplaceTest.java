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
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Exercise10ReplaceTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Test
    public void shouldReplaceWholeDocumentWithNewOne() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        // When
        Person updatedCharlieObject = new Person("charlie", "Charles the Suave", new Address("A new street", "GreatCity", 7654321),
                                                 Collections.<Integer>emptyList());
        Document findCharlie = new Document("_id", charlie.getId());
        UpdateResult resultOfUpdate = collection.replaceOne(findCharlie, PersonAdaptor.toDocument(updatedCharlieObject));

        // Then
        assertThat(resultOfUpdate.getModifiedCount(), is(1L));

        Document newCharlieDocument = collection.find(findCharlie).first();
        // all values should have been updated to the new object values
        assertThat(newCharlieDocument.getString("_id"), is(updatedCharlieObject.getId()));
        assertThat(newCharlieDocument.getString("name"), is(updatedCharlieObject.getName()));
        assertThat((List<Integer>) newCharlieDocument.get("books"), is(updatedCharlieObject.getBookIds()));
        Document address = (Document) newCharlieDocument.get("address");
        assertThat(address.getString("street"), is(updatedCharlieObject.getAddress().getStreet()));
        assertThat(address.getString("city"), is(updatedCharlieObject.getAddress().getTown()));
        assertThat(address.getInteger("phone"), is(updatedCharlieObject.getAddress().getPhone()));
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
