package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
public class Exercise4RetrieveTest {
    private DB database;
    private DBCollection collection;

    @Before
    public void setUp() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("Examples");
        collection = database.getCollection("people");
    }

    @Test
    public void shouldRetrieveBobFromTheDatabaseWhenHeIsTheOnlyOneInThere() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        // When
        // TODO: get this from querying the collection.  Hint: you can find just one
        DBObject result = null;

        // Then
        assertThat((String) result.get("_id"), is("bob"));
    }

    @Test
    public void shouldRetrieveEverythingFromTheDatabase() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        // When
        // TODO: get a cursor with everything in the database
        DBCursor cursor = null;

        // Then
        assertThat(cursor.size(), is(2));
        // they should come back in the same order they were put in
        assertThat((String) cursor.next().get("_id"), is("charlie"));
        assertThat((String) cursor.next().get("_id"), is("bob"));
    }

    @Test
    public void shouldSearchForAndReturnOnlyBobFromTheDatabaseWhenMorePeopleExist() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        // When
        // TODO create the query document
        DBObject query = null;
        DBCursor cursor = collection.find(query);

        // Then
        assertThat(cursor.count(), is(1));
        assertThat((String) cursor.one().get("name"), is("Bob The Amazing"));
    }

    @After
    public void tearDown() {
        database.dropDatabase();
    }
}
