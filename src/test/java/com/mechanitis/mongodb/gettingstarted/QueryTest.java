package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
import com.mongodb.BasicDBObject;
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
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class QueryTest {
    private DB database;
    private DBCollection collection;

    @Test
    public void shouldFindAllDBObjectsWithTheNameCharles() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        // When
        // TODO create the correct query to find Charlie by name
        DBObject query = null;
        // TODO use this query to get a List of matching Documents from the database
        List<DBObject> results = null;

        // Then
        assertThat(results.size(), is(1));
        assertThat((String) results.get(0).get("_id"), is(charlie.getId()));
    }

    @Test
    public void shouldFindAllDBObjectsWithTheNameCharlesAndOnlyReturnNameAndId() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        // When
        // TODO create the correct query to find Charlie by name (see above)
        DBObject query = null;
        // TODO use this query, combined with the "fields" selector, to get a list of result documents with only the name and ID fields
        List<DBObject> results = null;

        // Then
        assertThat(results.size(), is(1));
        DBObject theOnlyResult = results.get(0);
        assertThat((String) theOnlyResult.get("_id"), is(charlie.getId()));
        assertThat((String) theOnlyResult.get("name"), is(charlie.getName()));
        assertThat(theOnlyResult.get("address"), is(nullValue()));
        assertThat(theOnlyResult.get("books"), is(nullValue()));
    }

    //BONUS
    @Test
    public void shouldFindAllDBObjectsWithTheNameCharlesAndExcludeAddressInReturn() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));

        // When
        // TODO create the correct query to find Charlie by name (see above)
        DBObject query = null;
        // TODO use this query, combined with the "fields" selector, to get a list of result documents without address subdocument
        List<DBObject> results = null;

        // Then
        assertThat(results.size(), is(1));
        DBObject theOnlyResult = results.get(0);
        assertThat((String) theOnlyResult.get("_id"), is(charlie.getId()));
        assertThat((String) theOnlyResult.get("name"), is(charlie.getName()));
        assertThat(theOnlyResult.get("address"), is(nullValue()));
        assertThat((List<Integer>) theOnlyResult.get("books"), is(charlie.getBookIds()));
    }

    //BONUS
    @Test
    public void shouldReturnADBObjectWithAPhoneNumberLessThan1000000000() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(PersonAdaptor.toDBObject(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 987654321), asList(27464, 747854));
        collection.insert(PersonAdaptor.toDBObject(bob));


        // When
        //TODO build up a query which checks the numeric value
        DBObject query = null;
        // TODO use this query to get a List of matching Documents from the database
        DBCursor results = null;

        assertThat(results.size(), is(1));
        assertThat((String) results.next().get("_id"), is(bob.getId()));
    }

    //BONUS
    @Test
    public void shouldReturnDBObjects3to9Of20DBObjectsUsingSkipAndLimit() {
        // Given
        for (int i = 0; i < 20; i++) {
            collection.insert(new BasicDBObject("name", "person" + i).append("someIntValue", i));
        }

        // When
        // TODO no need for a query, just combine the find with the other operators available
        List<DBObject> results = null;

        // Then
        assertThat(results.size(), is(7));
        assertThat((int) results.get(0).get("someIntValue"), is(3));
        assertThat((int) results.get(1).get("someIntValue"), is(4));
        assertThat((int) results.get(2).get("someIntValue"), is(5));
        assertThat((int) results.get(3).get("someIntValue"), is(6));
        assertThat((int) results.get(4).get("someIntValue"), is(7));
        assertThat((int) results.get(5).get("someIntValue"), is(8));
        assertThat((int) results.get(6).get("someIntValue"), is(9));
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
