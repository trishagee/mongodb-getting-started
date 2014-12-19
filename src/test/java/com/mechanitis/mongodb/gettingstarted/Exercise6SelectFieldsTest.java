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
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class Exercise6SelectFieldsTest {
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Test
    public void shouldFindAllDocumentsWithTheNameCharlesAndOnlyReturnNameAndId() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        // When
        // TODO create the correct query to find Charlie by name (see above)
        Document query = null;
        // TODO use this query, combined with projection, to get a list of result documents with only the name and ID fields
        MongoCursor<Document> results = collection.find(query).projection(new Document("name", 1)).iterator();

        // Then
        Document theOnlyResult = results.next();
        assertThat((String) theOnlyResult.get("_id"), is(charlie.getId()));
        assertThat((String) theOnlyResult.get("name"), is(charlie.getName()));
        assertThat(theOnlyResult.get("address"), is(nullValue()));
        assertThat(theOnlyResult.get("books"), is(nullValue()));
        assertThat(results.hasNext(), is(false));
    }

    //BONUS
    @Test
    public void shouldFindAllDocumentsWithTheNameCharlesAndExcludeAddressInReturn() {
        // Given
        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));
        collection.insertOne(PersonAdaptor.toDocument(bob));

        // When
        // TODO create the correct query to find Charlie by name (see above)
        Document query = null;
        // TODO use this query, combined with projection, to get a list of result documents without address subdocument
        MongoCursor<Document> results = null;

        // Then
        Document theOnlyResult = results.next();
        assertThat(theOnlyResult.getString("_id"), is(charlie.getId()));
        assertThat(theOnlyResult.getString("name"), is(charlie.getName()));
        assertThat(theOnlyResult.get("address"), is(nullValue()));
        assertThat((List<Integer>) theOnlyResult.get("books"), is(charlie.getBookIds()));
        assertThat(results.hasNext(), is(false));
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
