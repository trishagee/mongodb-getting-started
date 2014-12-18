package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteResult;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import junit.framework.Assert;
import org.bson.Document;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Collections;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Exercise13UpsertTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Ignore("Behaviour changed in 3.0 driver")
    @Test
    public void shouldOnlyInsertDBObjectIfItDidNotExistWhenUpsertIsTrue() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        // new person not in the database yet
        Person claire = new Person("claire", "Claire", new Address("1", "Town", 836558493), Collections.<Integer>emptyList());

        // When
        Document findClaire = new Document("_id", claire.getId());
        UpdateResult resultOfUpdate = collection.updateOne(findClaire, PersonAdaptor.toDocument(claire));

        // Then
        assertThat(resultOfUpdate.getModifiedCount(), is(0L));
        // without upsert this should not have been inserted
//        assertThat(collection.find(findClaire).count(), is(0));


        // When
//        WriteResult resultOfUpsert = collection.updateOne(findClaire, PersonAdaptor.toDocument(claire), true, false);

        // Then
//        assertThat(resultOfUpsert.getN(), is(1));
//
//        DBObject newClaireDBObject = collection.find(findClaire).toArray().get(0);
//        // all values should have been updated to the new object values
//        assertThat((String) newClaireDBObject.get("_id"), is(claire.getId()));
        Assert.fail("Implement Me");
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
