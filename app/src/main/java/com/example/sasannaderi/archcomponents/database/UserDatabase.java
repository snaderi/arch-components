package com.example.sasannaderi.archcomponents.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.sasannaderi.archcomponents.models.User;

/**
 * Created by sasannaderi on 2017-06-20.
 */

@Database(entities = {User.class}, version = 2)
public abstract class UserDatabase extends RoomDatabase {

    abstract public UserDao userDao();

}
