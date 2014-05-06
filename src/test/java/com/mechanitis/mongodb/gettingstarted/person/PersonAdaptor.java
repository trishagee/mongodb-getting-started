package com.mechanitis.mongodb.gettingstarted.person;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * This Adaptor allows us to separate our domain object, Person, from our library-specific classes, in this case the MongoDB-specific
 * DBObject.
 */
public final class PersonAdaptor {
    public static final DBObject toDBObject(Person person) {
        DBObject addressAsDBObject = new BasicDBObject("street", person.getAddress().getStreet())
                                     .append("city", person.getAddress().getTown())
                                     .append("phone", person.getAddress().getPhone());
        return new BasicDBObject("_id", person.getId())
               .append("name", person.getName())
               .append("address", addressAsDBObject)
               .append("books", person.getBookIds());
    }
}
