package com.example.sasannaderi.archcomponents.models;

import android.arch.persistence.room.Embedded;

/**
 * Created by sasannaderi on 2017-09-01.
 */

public class Address {

    public String street;

    public String suite;

    public String city;

    public String zipcode;

    @Embedded
    public Geo geo;

    @Override
    public String toString() {
        return String.format("%s %s\n%s, %s", street, suite, city, zipcode);
    }
}
