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
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Exercise11UpdateAFieldTest {
    private DB database;
    private DBCollection collection;

    @Test
    public void shouldUpdateCharliesAddress() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        String charliesNewAddress = "987 The New Street";

        // When
        // TODO create query to find Charlie by ID
        DBObject findCharlie = null;
        // TODO use the query to find charlie and update his street with the new address
        WriteResult resultOfUpdate = null;

        // Then
        assertThat(resultOfUpdate.getN(), is(1));

        DBObject newCharlie = collection.find(findCharlie).toArray().get(0);
        // this stuff should all be the same
        assertThat((String) newCharlie.get("_id"), is(charlie.getId()));
        assertThat((String) newCharlie.get("name"), is(charlie.getName()));

        // the address street, and only the street, should have changed
        DBObject address = (DBObject) newCharlie.get("address");
        assertThat((String) address.get("street"), is(charliesNewAddress));
        assertThat((String) address.get("city"), is(charlie.getAddress().getTown()));
        assertThat((int) address.get("phone"), is(charlie.getAddress().getPhone()));
    }

    @Test
    public void shouldAddANewFieldToAnExistingDocument() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        // When
        // TODO create query to find Charlie by ID
        DBObject findCharlie = null;
        // TODO use the query to find charlie and update his street with the new address
        WriteResult resultOfUpdate = null;

        // Then
        assertThat(resultOfUpdate.getN(), is(1));

        DBObject newCharlie = collection.find(findCharlie).toArray().get(0);
        // this stuff should all be the same
        assertThat((String) newCharlie.get("_id"), is(charlie.getId()));
        assertThat((String) newCharlie.get("name"), is(charlie.getName()));
        assertThat((String) newCharlie.get("newField"), is("A New Value"));
    }

    //BONUS
    @Test
    public void shouldAddAnotherBookToBobsBookIds() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        // When
        // TODO create query to find Bob by ID
        DBObject findBob = null;
        // TODO update the only Bob document to add the ID '66' to the array of Book IDs

        // Then
        DBObject newBob = collection.find(findBob).toArray().get(0);

        assertThat((String) newBob.get("name"), is(bob.getName()));

        // there should be another item in the array
        List<Integer> bobsBooks = (List<Integer>) newBob.get("books");
        // note these are  ordered
        assertThat(bobsBooks.size(), is(3));
        assertThat(bobsBooks.get(0), is(27464));
        assertThat(bobsBooks.get(1), is(747854));
        assertThat(bobsBooks.get(2), is(66));
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
