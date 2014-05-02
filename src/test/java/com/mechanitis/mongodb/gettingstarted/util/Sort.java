package com.mechanitis.mongodb.gettingstarted.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class Sort {
    public static DBObject ascending(String fieldName) {
        return new BasicDBObject(fieldName, 1);
    }
}
