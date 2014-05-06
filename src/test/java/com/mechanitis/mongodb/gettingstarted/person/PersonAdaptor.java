package com.mechanitis.mongodb.gettingstarted.person;

import com.mongodb.DBObject;

/**
 * This Adaptor allows us to separate our domain object, Person, from our library-specific classes, in this case the MongoDB-specific
 * DBObject.
 */
public final class PersonAdaptor {
    public static final DBObject toDBObject(Person person) {
        throw new UnsupportedOperationException("You need to implement this");
    }
}
