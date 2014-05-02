package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
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
import java.util.ArrayList;
import java.util.List;

import static com.mechanitis.mongodb.gettingstarted.util.Sort.ascending;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
public class RetrieveTest {
    private DB database;
    private DBCollection collection;

    @Before
    public void setUp() throws UnknownHostException {
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        database = mongoClient.getDB("JAXDatabase");
        collection = database.getCollection("people");
    }

    @Test
    public void shouldRetrieveBobFromTheDatabase() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(bob.toDBObject());

        // When
        DBObject result = collection.find().one();

        // Then
        assertThat((String) result.get("_id"), is("bob"));
    }

    @Test
    public void shouldRetrieveEverythingFromTheDatabase() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(charlie.toDBObject());

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(bob.toDBObject());

        // When
        List<DBObject> results = collection.find().sort(ascending("_id")).toArray();
        //TODO sorting needed to ensure return order

        // Then
        assertThat(results.size(), is(2));
        assertThat((String) results.get(0).get("_id"), is("bob"));
        assertThat((String) results.get(1).get("_id"), is("charlie"));
    }

    @Test
    public void shouldSearchForAndReturnOnlyBobFromTheDatabaseWhenMorePeopleExist() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(charlie.toDBObject());

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(bob.toDBObject());

        // When
        DBObject query = new BasicDBObject("_id", "bob");
        DBCursor mongoView = collection.find(query);

        // Then
        assertThat(mongoView.count(), is(1));
        assertThat((String) mongoView.one().get("name"), is("Bob The Amazing"));
    }

    @Test
    public void shouldConstructListOfPersonFromQueryResults() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insert(charlie.toDBObject());

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insert(bob.toDBObject());

        // When
        final List<Person> results = new ArrayList<>();
//        collection.find().forEach(new Block<DBObject>() {
//            @Override
//            public void apply(final DBObject DBObject) {
//                DBObject addressDBObject = (DBObject) DBObject.get("address");
//                Person person = new Person((String) DBObject.get("_id"),
//                                           (String) DBObject.get("name"),
//                                           new Address((String) addressDBObject.get("street"),
//                                                       (String) addressDBObject.get("city"),
//                                                       (int) addressDBObject.get("phone")),
//                                           ((List<Integer>) DBObject.get("books")));
//                results.add(person);
//            }
//        });

        // Then
        assertThat(results.size(), is(2));
        assertThat(results, contains(charlie, bob));
    }

//    @Test
//    public void shouldConstructListOfPersonFromQueryResultsJava8() {
//        // Given
//        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
//        collection.insert(charlie.toDBObject());
//
//        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
//        collection.insert(bob.toDBObject());
//
//        // When
//        List<Person> results = new ArrayList<>();
//        collection.find().forEach(DBObject -> {
//                DBObject addressDBObject = (DBObject) DBObject.get("address");
//                Person person = new Person((String) DBObject.get("_id"),
//                                           (String) DBObject.get("name"),
//                                           new Address((String) addressDBObject.get("street"),
//                                                       (String) addressDBObject.get("city"),
//                                                       (int) addressDBObject.get("phone")),
//                                           ((List<Integer>) DBObject.get("books")));
//                return results.add(person);
//        });
//
//        // Then
//        assertThat(results.size(), is(2));
//        assertThat(results, contains(charlie, bob));
//    }


    @After
    public void tearDown() {
        database.dropDatabase();
    }
}
