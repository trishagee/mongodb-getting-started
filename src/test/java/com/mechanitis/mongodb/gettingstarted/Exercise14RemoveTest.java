package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class Exercise14RemoveTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Test
    public void shouldDeleteOnlyCharlieFromTheDatabase() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        Person emily = new Person("emily", "Emily", new Address("5", "Some Town", 646383), Collections.<Integer>emptyList());
        collection.insertOne(PersonAdaptor.toDocument(emily));

        // When
        Document query = new Document("_id", "charlie");
        DeleteResult resultOfRemove = collection.deleteOne(query);

        // Then
        assertThat(resultOfRemove.getDeletedCount(), is(1L));

        MongoCursor<Document> remainingPeople = collection.find().iterator();

        int size = 0;
        while(remainingPeople.hasNext()) {
            assertThat(remainingPeople.next().getString("_id"), is(not(charlie.getId())));
            size++;
        }
        assertThat(size, is(2));
    }

    @Test
    public void shouldDeletePeopleWhoLiveInLondon() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        Person emily = new Person("emily", "Emily", new Address("5", "Some Town", 646383), Collections.<Integer>emptyList());
        collection.insertOne(PersonAdaptor.toDocument(emily));

        // When
        Document query = new Document("address.city", "LondonTown");
        DeleteResult resultOfRemove = collection.deleteMany(query);

        // Then
        assertThat(resultOfRemove.getDeletedCount(), is(2L));

        assertThat(collection.count(), is(1L));

        assertThat(collection.find().first().getString("_id"), is(emily.getId()));
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
