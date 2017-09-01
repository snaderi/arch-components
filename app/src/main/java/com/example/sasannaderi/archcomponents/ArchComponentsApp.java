package com.example.sasannaderi.archcomponents;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.sasannaderi.archcomponents.database.UserDatabase;

/**
 * Created by sasannaderi on 2017-06-20.
 */

public class ArchComponentsApp extends Application {

    private static UserDatabase sUserDatabase;

    @Override
    public void onCreate() {
        super.onCreate();

        sUserDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "my-database").build();
    }

    public static UserDatabase getUserDatabase() {
        return sUserDatabase;
    }
}
