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
import com.mongodb.WriteResult;
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

public class UpdateTest {
    private DB database;
    private DBCollection collection;

    // Update
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
        DBObject findCharlie = new BasicDBObject("_id", charlie.getId());
        WriteResult resultOfUpdate = collection.update(findCharlie,
                                                       new BasicDBObject("$set", new BasicDBObject("newField", "A New Value")));

        // Then
        assertThat(resultOfUpdate.getN(), is(1));

        DBObject newCharlie = collection.find(findCharlie).toArray().get(0);
        // this stuff should all be the same
        assertThat((String) newCharlie.get("_id"), is(charlie.getId()));
        assertThat((String) newCharlie.get("name"), is(charlie.getName()));
        assertThat((String) newCharlie.get("newField"), is("A New Value"));
    }

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

    // BONUS
    @Test
    public void shouldReplaceWholeDBObjectWithNewOne() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        // When
        Person updatedCharlieObject = new Person("charlie", "Charles the Suave", new Address("A new street", "GreatCity", 7654321),
                                                 Collections.<Integer>emptyList());
        // TODO create query to find Charlie by ID
        DBObject findCharlie = null;
        // TODO do an update replacing the whole previous Document with the new one
        WriteResult resultOfUpdate = null;

        // Then
        assertThat(resultOfUpdate.getN(), is(1));

        DBObject newCharlieDBObject = collection.find(findCharlie).toArray().get(0);
        // all values should have been updated to the new object values
        assertThat((String) newCharlieDBObject.get("_id"), is(updatedCharlieObject.getId()));
        assertThat((String) newCharlieDBObject.get("name"), is(updatedCharlieObject.getName()));
        assertThat((List<Integer>) newCharlieDBObject.get("books"), is(updatedCharlieObject.getBookIds()));
        DBObject address = (DBObject) newCharlieDBObject.get("address");
        assertThat((String) address.get("street"), is(updatedCharlieObject.getAddress().getStreet()));
        assertThat((String) address.get("city"), is(updatedCharlieObject.getAddress().getTown()));
        assertThat((int) address.get("phone"), is(updatedCharlieObject.getAddress().getPhone()));
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
