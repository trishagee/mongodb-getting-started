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

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Exercise13UpsertTest {
    private DB database;
    private DBCollection collection;

    //Upsert
    @Test
    public void shouldOnlyInsertDBObjectIfItDidNotExistWhenUpsertIsTrue() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        // new person not in the database yet
        Person claire = new Person("claire", "Claire", new Address("1", "Town", 836558493), Collections.<Integer>emptyList());

        // When
        // TODO create query to find Claire by ID
        DBObject findClaire = null;
        // TODO Perform an update with this new person to show it does NOT get added to the database
        WriteResult resultOfUpdate = null;

        // Then
        assertThat(resultOfUpdate.getN(), is(0));
        // without upsert this should not have been inserted
        assertThat(collection.find(findClaire).count(), is(0));


        // When
        // TODO Perform an update with this new person to show it DOES get added to the database
        WriteResult resultOfUpsert = null;

        // Then
        assertThat(resultOfUpsert.getN(), is(1));

        DBObject newClaireDBObject = collection.find(findClaire).toArray().get(0);
        // all values should have been updated to the new object values
        assertThat((String) newClaireDBObject.get("_id"), is(claire.getId()));
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
