package com.example.sasannaderi.archcomponents.api;

import android.arch.lifecycle.LiveData;

import com.example.sasannaderi.archcomponents.api.responses.ApiResponse;
import com.example.sasannaderi.archcomponents.models.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sasannaderi on 2017-06-19.
 */

public interface UserEndpoints {

    @GET("/users")
    LiveData<ApiResponse<List<User>>> getUsers();

    @GET("/users/{user}")
    LiveData<ApiResponse<User>> getUser(@Path("user") int user);
}
