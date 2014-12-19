package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
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
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class Exercise12UpdateMultipleDocumentsTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    //Multi=false
    @Test
    public void shouldOnlyUpdateTheFirstDocumentMatchingTheQuery() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        Person emily = new Person("emily", "Emily", new Address("5", "Some Town", 646383), Collections.<Integer>emptyList());
        collection.insertOne(PersonAdaptor.toDocument(emily));

        // When
        // TODO create query to find everyone with 'LondonTown' as their city
        Document findLondoners = null;
        assertThat(collection.count(findLondoners), is(2L));

        // TODO update only the first Londoner here to have a new field, "wasUpdated", with a value of true

        // Then
        MongoCursor<Document> londoners = collection.find(findLondoners).sort(new Document("_id", 1)).iterator();

        Document firstLondoner = londoners.next();
        assertThat(firstLondoner.getString("name"), is(bob.getName()));
        assertThat(firstLondoner.getBoolean("wasUpdated"), is(true));

        Document secondLondoner = londoners.next();
        assertThat(secondLondoner.getString("name"), is(charlie.getName()));
        assertThat(secondLondoner.get("wasUpdated"), is(nullValue()));

        assertThat(londoners.hasNext(), is(false));
    }

    //Multi=true
    @Test
    public void shouldUpdateEveryoneLivingInLondon() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        Person emily = new Person("emily", "Emily", new Address("5", "Some Town", 646383), Collections.<Integer>emptyList());
        collection.insertOne(PersonAdaptor.toDocument(emily));

        // When
        // TODO create query to find everyone with 'LondonTown' as their city
        Document findLondoners = null;
        assertThat(collection.count(findLondoners), is(2L));

        // TODO update all Londoners here to have a new field, "wasUpdated", with a value of true

        // Then
        MongoCursor<Document> londoners = collection.find(findLondoners).sort(new Document("_id", 1)).iterator();

        Document firstLondoner = londoners.next();
        assertThat(firstLondoner.getString("name"), is(bob.getName()));
        assertThat(firstLondoner.getBoolean("wasUpdated"), is(true));

        Document secondLondoner = londoners.next();
        assertThat(secondLondoner.getString("name"), is(charlie.getName()));
        assertThat(secondLondoner.getBoolean("wasUpdated"), is(true));

        assertThat(londoners.hasNext(), is(false));
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
