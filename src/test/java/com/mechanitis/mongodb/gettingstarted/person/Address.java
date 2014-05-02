package com.mechanitis.mongodb.gettingstarted.person;

public class Address {
    private final String street;
    private final String town;
    private final int phone;

    public Address(final String street, final String town, final int phone) {
        this.street = street;
        this.town = town;
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }

    public int getPhone() {
        return phone;
    }
}
