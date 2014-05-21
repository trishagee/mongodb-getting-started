package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class Exercise14RemoveTest {
    private DB database;
    private DBCollection collection;

    @Test
    public void shouldDeleteOnlyCharlieFromTheDatabase() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person emily = new Person("emily", "Emily", new Address("5", "Some Town", 646383), Collections.<Integer>emptyList());
        collection.insert(PersonAdaptor.toDBObject(emily));

        // When
        // TODO create a query to find charlie by ID
        DBObject query = null;
        // TODO execute the remove
        WriteResult resultOfRemove = null;

        // Then
        assertThat(resultOfRemove.getN(), is(1));

        List<DBObject> remainingPeople = collection.find().toArray();
        assertThat(remainingPeople.size(), is(2));

        for (final DBObject remainingPerson : remainingPeople) {
            assertThat((String) remainingPerson.get("_id"), is(not(charlie.getId())));
        }
    }

    @Test
    public void shouldDeletePeopleWhoLiveInLondon() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person emily = new Person("emily", "Emily", new Address("5", "Some Town", 646383), Collections.<Integer>emptyList());
        collection.insert(PersonAdaptor.toDBObject(emily));

        // When
        // TODO create the query to check the city field inside the address subdocument for 'LondonTown'
        DBObject query = null;
        // TODO execute the remove
        WriteResult resultOfRemove = null;

        // Then
        assertThat(resultOfRemove.getN(), is(2));

        List<DBObject> remainingPeople = collection.find().toArray();
        assertThat(remainingPeople.size(), is(1));

        assertThat((String) remainingPeople.get(0).get("_id"), is(emily.getId()));
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
