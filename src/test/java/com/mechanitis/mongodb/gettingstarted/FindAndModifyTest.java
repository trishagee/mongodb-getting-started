package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FindAndModifyTest {
    private DB database;
    private DBCollection collection;

//    @Test
//    public void shouldUpdateDBObjectAndReturnOriginalDBObject() {
//        // Given
//        DBObject order1 = new BasicDBObject("orderNo", 1L).append("product", "Some Book").append("status", "PENDING");
//        collection.insert(order1);
//
//        // When
//        DBObject query = new BasicDBObject("orderNo", 1L);
//        DBObject resultOfFindAndModify = collection.find(query)
//                                                   .getOneAndUpdate(new BasicDBObject("$set", new BasicDBObject("status", "APPROVED")));
//
//        // Then
//        assertThat((String) resultOfFindAndModify.get("status"), is("PENDING"));
//        assertThat((String) collection.find(query).getOne().get("status"), is("APPROVED"));
//    }
//
//    @Test
//    public void shouldUpdateDBObjectAndReturnNewDBObject() {
//        // Given
//        DBObject order1 = new BasicDBObject("orderNo", 1L).append("product", "Some Book").append("quantity", 2);
//        collection.insert(order1);
//
//        // When
//        DBObject query = new BasicDBObject("orderNo", 1L);
//        DBObject resultOfFindAndModify = collection.find(query)
//                                                   .updateOneAndGet(new BasicDBObject("$inc", new BasicDBObject("quantity", 1)));
//
//        // Then
//        assertThat((int) resultOfFindAndModify.get("quantity"), is(3));
//    }
//
//    @Test
//    public void shouldReplaceDBObjectAndReturnTheOriginalDBObject() {
//        // Given
//        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
//        collection.insert(charlie.toDBObject());
//
//        // When
//        Person updatedCharlieObject = new Person("charlie", "Charles the Suave", new Address("A new street", "GreatCity", 7654321),
//                                                 Collections.<Integer>emptyList());
//        DBObject findCharlie = new BasicDBObject("_id", charlie.getId());
//        DBObject resultOfFindAndModify = collection.find(findCharlie)
//                                                   .getOneAndReplace(updatedCharlieObject.toDBObject());
//
//        // Then
//        // the returned object should be the original one
//        assertThat((String) resultOfFindAndModify.get("_id"), is(charlie.getId()));
//        assertThat((String) resultOfFindAndModify.get("name"), is(charlie.getName()));
//        assertThat((List<Integer>) resultOfFindAndModify.get("books"), is(charlie.getBookIds()));
//        DBObject address = (DBObject) resultOfFindAndModify.get("address");
//        assertThat((String) address.get("street"), is(charlie.getAddress().getStreet()));
//        assertThat((String) address.get("city"), is(charlie.getAddress().getTown()));
//        assertThat((int) address.get("phone"), is(charlie.getAddress().getPhone()));
//
//        // but the change should have been made in the database
//        DBObject charlieInDatabase = collection.find(findCharlie).getOne();
//        assertThat((String) charlieInDatabase.get("_id"), is(updatedCharlieObject.getId()));
//        assertThat((String) charlieInDatabase.get("name"), is(updatedCharlieObject.getName()));
//        assertThat((List<Integer>) charlieInDatabase.get("books"), is(updatedCharlieObject.getBookIds()));
//        address = (DBObject) charlieInDatabase.get("address");
//        assertThat((String) address.get("street"), is(updatedCharlieObject.getAddress().getStreet()));
//        assertThat((String) address.get("city"), is(updatedCharlieObject.getAddress().getTown()));
//        assertThat((int) address.get("phone"), is(updatedCharlieObject.getAddress().getPhone()));
//    }
//
//    @Test
//    public void shouldReplaceDBObjectAndReturnTheNewDBObject() {
//        // Given
//        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
//        collection.insert(bob.toDBObject());
//
//        Person originalCharlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
//        collection.insert(originalCharlie.toDBObject());
//
//        // When
//        Person updatedCharlieObject = new Person("charlie", "Charles the Suave", new Address("A new street", "GreatCity", 7654321),
//                                                 Collections.<Integer>emptyList());
//        DBObject findCharlie = new BasicDBObject("_id", originalCharlie.getId());
//        DBObject resultOfFindAndModify = collection.find(findCharlie)
//                                                   .replaceOneAndGet(updatedCharlieObject.toDBObject());
//
//        // Then
//        // all values should have been updated to the new object values
//        assertThat((String) resultOfFindAndModify.get("_id"), is(updatedCharlieObject.getId()));
//        assertThat((String) resultOfFindAndModify.get("name"), is(updatedCharlieObject.getName()));
//        assertThat((List<Integer>) resultOfFindAndModify.get("books"), is(updatedCharlieObject.getBookIds()));
//        DBObject address = (DBObject) resultOfFindAndModify.get("address");
//        assertThat((String) address.get("street"), is(updatedCharlieObject.getAddress().getStreet()));
//        assertThat((String) address.get("city"), is(updatedCharlieObject.getAddress().getTown()));
//        assertThat((int) address.get("phone"), is(updatedCharlieObject.getAddress().getPhone()));
//    }
//
//
//    @Test
//    public void shouldRemoveDBObjectAndReturnTheOriginalDBObject() {
//        // Given
//        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
//        collection.insert(charlie.toDBObject());
//
//        // When
//        DBObject findCharlie = new BasicDBObject("_id", charlie.getId());
//        DBObject resultOfFindAndModify = collection.find(findCharlie)
//                                                   .getOneAndRemove();
//
//        // Then
//        // the returned object should be the DBObject before it was deleted (frankly the other way around would be dumb)
//        assertThat((String) resultOfFindAndModify.get("_id"), is(charlie.getId()));
//        assertThat((String) resultOfFindAndModify.get("name"), is(charlie.getName()));
//        assertThat((List<Integer>) resultOfFindAndModify.get("books"), is(charlie.getBookIds()));
//        DBObject address = (DBObject) resultOfFindAndModify.get("address");
//        assertThat((String) address.get("street"), is(charlie.getAddress().getStreet()));
//        assertThat((String) address.get("city"), is(charlie.getAddress().getTown()));
//        assertThat((int) address.get("phone"), is(charlie.getAddress().getPhone()));
//
//        // but obviously it should have been deleted in the database
//        assertThat(collection.find(findCharlie).count(), is(0L));
//    }

    @Before
    public void setUp() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("JAXDatabase");
        collection = database.getCollection("people");
    }

    @After
    public void tearDown() {
        database.dropDatabase();
    }
}
