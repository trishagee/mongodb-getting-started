package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
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
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class Exercise12UpdateMultipleDocumentsTest {
    private DB database;
    private DBCollection collection;

    //Multi=false
    @Test
    public void shouldOnlyUpdateTheFirstDBObjectMatchingTheQuery() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person emily = new Person("emily", "Emily", new Address("5", "Some Town", 646383), Collections.<Integer>emptyList());
        collection.insert(PersonAdaptor.toDBObject(emily));

        // When
        // TODO create query to find everyone with 'LondonTown' as their city
        DBObject findLondoners = null;
        assertThat(collection.find(findLondoners).count(), is(2));

        // TODO update only the first Londonder here to have a new field, "wasUpdated", with a value of true

        
        // Then
        List<DBObject> londoners = collection.find(findLondoners).sort(new BasicDBObject("_id", 1)).toArray();
        assertThat(londoners.size(), is(2));

        assertThat((String) londoners.get(0).get("name"), is(bob.getName()));
        assertThat((boolean) londoners.get(0).get("wasUpdated"), is(true));

        assertThat((String) londoners.get(1).get("name"), is(charlie.getName()));
        assertThat(londoners.get(1).get("wasUpdated"), is(nullValue()));
    }

    //Multi=true
    @Test
    public void shouldUpdateEveryoneLivingInLondon() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person emily = new Person("emily", "Emily", new Address("5", "Some Town", 646383), Collections.<Integer>emptyList());
        collection.insert(PersonAdaptor.toDBObject(emily));

        // When
        // TODO create query to find everyone with 'LondonTown' as their city
        DBObject findLondoners = null;
        assertThat(collection.find(findLondoners).count(), is(2));

        // TODO update all Londonders here to have a new field, "wasUpdated", with a value of true

        
        // Then
        List<DBObject> londoners = collection.find(findLondoners).sort(new BasicDBObject("_id", 1)).toArray();
        assertThat(londoners.size(), is(2));

        DBObject firstLondoner = londoners.get(0);
        assertThat((String) firstLondoner.get("name"), is(bob.getName()));
        assertThat((boolean) firstLondoner.get("wasUpdated"), is(true));

        DBObject secondLondoner = londoners.get(1);
        assertThat((String) secondLondoner.get("name"), is(charlie.getName()));
        assertThat((boolean) secondLondoner.get("wasUpdated"), is(true));
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
