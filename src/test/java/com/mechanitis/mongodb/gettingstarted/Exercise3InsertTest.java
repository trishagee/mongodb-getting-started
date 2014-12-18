package com.mechanitis.mongodb.gettingstarted;

import com.mechanitis.mongodb.gettingstarted.person.Address;
import com.mechanitis.mongodb.gettingstarted.person.Person;
import com.mechanitis.mongodb.gettingstarted.person.PersonAdaptor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.net.UnknownHostException;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Exercise3InsertTest {
    @Test
    public void shouldTurnAPersonIntoADocument() {
        // Given
        Person bob = new Person("bob", "Bob The Amazing", new Address("123 Fake St", "LondonTown", 1234567890), asList(27464, 747854));

        // When
        Document bobAsDocument = PersonAdaptor.toDocument(bob);

        // Then
        String expectedDocument = "{" +
                                  " '_id' : 'bob' ," +
                                  " 'name' : 'Bob The Amazing' ," +
                                  " 'address' : {" +
                                    " 'street' : '123 Fake St' ," +
                                    " 'city' : 'LondonTown' ," +
                                    " 'phone' : 1234567890" +
                                  "} ," +
                                  " 'books' : [ 27464 , 747854]" +
                                  "}";
        assertThat(bobAsDocument, is(Document.valueOf(expectedDocument)));
    }

    @Test
    public void shouldBeAbleToSaveAPerson() throws UnknownHostException {
        // Given
        MongoClient mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        MongoDatabase database = mongoClient.getDatabase("Examples");
        MongoCollection<Document> collection = database.getCollection("people");

        Person charlie = new Person("charlie", "Charles", new Address("74 That Place", "LondonTown", 1234567890), asList(1, 74));

        // When
        collection.insertOne(PersonAdaptor.toDocument(charlie));

        // Then
        assertThat(collection.count(), is(1L));

        // Clean up
        database.dropDatabase();
    }
}
