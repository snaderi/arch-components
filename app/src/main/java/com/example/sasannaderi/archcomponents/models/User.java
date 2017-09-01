package com.example.sasannaderi.archcomponents.models;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by sasannaderi on 2017-06-19.
 */
@Entity
public class User {

    @PrimaryKey
    public int id;

    public String name;

    public String email;

    public String username;

    public String phone;

    public String website;

    @Embedded
    public Address address;

    @Embedded
    public Company company;

    @Override
    public String toString() {
        return name;
    }
}
