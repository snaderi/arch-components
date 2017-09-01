package com.example.sasannaderi.archcomponents.models.repositories;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.sasannaderi.archcomponents.ArchComponentsApp;
import com.example.sasannaderi.archcomponents.api.UserEndpoints;
import com.example.sasannaderi.archcomponents.api.WebService;
import com.example.sasannaderi.archcomponents.api.responses.ApiResponse;
import com.example.sasannaderi.archcomponents.database.UserDao;
import com.example.sasannaderi.archcomponents.models.User;

import java.util.List;

/**
 * Created by sasannaderi on 2017-06-19.
 */

public class UserRepository implements Repository<User> {

    private UserDao mUserDao;
    private UserEndpoints mUserEndpoints;

    public UserRepository() {
        mUserDao = ArchComponentsApp.getUserDatabase().userDao();
        mUserEndpoints = WebService.Generator.createService(UserEndpoints.class);
    }

    @Override
    public LiveData<Resource<User>> get(final int id) {
        return new NetworkBoundResource<User, User>() {

            @Override
            protected void saveCallResult(@NonNull final User user) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        mUserDao.insertUser(user);
                        return null;
                    }
                }.execute();
            }

            @Override
            protected boolean shouldFetch(@Nullable User data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<User> loadFromDb() {
                return mUserDao.loadUser(id);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<User>> createCall() {
                return mUserEndpoints.getUser(id);
            }
        }.asLiveData();
    }

    @Override
    public LiveData<Resource<List<User>>> getAll() {
        return new NetworkBoundResource<List<User>, List<User>>() {

            @Override
            protected void saveCallResult(@NonNull final List<User> users) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        mUserDao.insertUsers(users);
                        return null;
                    }
                }.execute();
            }

            @Override
            protected boolean shouldFetch(@Nullable List<User> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<User>> loadFromDb() {
                return mUserDao.loadUsers();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<User>>> createCall() {
                return mUserEndpoints.getUsers();
            }
        }.asLiveData();
    }
}
