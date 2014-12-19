package com.mechanitis.mongodb.gettingstarted.person;

import org.bson.Document;

/**
 * This Adaptor allows us to separate our domain object, Person, from our library-specific classes, in this case the MongoDB-specific
 * Document.
 */
public final class PersonAdaptor {
    private PersonAdaptor() {
    }

    public static Document toDocument(final Person person) {
        throw new UnsupportedOperationException("You need to implement this");
    }
}
