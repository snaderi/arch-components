package com.example.sasannaderi.archcomponents.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.sasannaderi.archcomponents.models.User;

import java.util.List;

/**
 * Created by sasannaderi on 2017-06-20.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertUsers(List<User> users);

    @Query("SELECT * from User WHERE id = :id")
    public abstract LiveData<User> loadUser(int id);

    @Query("SELECT * FROM User")
    public abstract LiveData<List<User>> loadUsers();
}
