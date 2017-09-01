package com.example.sasannaderi.archcomponents.models;

import android.arch.persistence.room.ColumnInfo;

/**
 * Created by sasannaderi on 2017-09-01.
 */

public class Company {

    @ColumnInfo(name = "company_name")
    public String name;

    public String catchPhrase;

    public String bs;

    @Override
    public String toString() {
        return name;
    }
}
