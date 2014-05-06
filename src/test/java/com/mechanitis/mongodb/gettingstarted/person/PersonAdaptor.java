package com.mechanitis.mongodb.gettingstarted.person;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * This Adaptor allows us to separate our domain object, Person, from our library-specific classes, in this case the MongoDB-specific
 * DBObject.
 */
public final class PersonAdaptor {
    public static final DBObject toDBObject(Person person) {
        return new BasicDBObject("_id", person.getId())
               .append("name", person.getName())
               .append("address", toDBObject(person.getAddress()))
               .append("books", person.getBookIds());
    }

    private static DBObject toDBObject(Address address) {
        return new BasicDBObject("street", address.getStreet())
               .append("city", address.getTown())
               .append("phone", address.getPhone());
    }
}
