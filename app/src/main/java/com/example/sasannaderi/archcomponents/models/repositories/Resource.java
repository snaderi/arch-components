package com.example.sasannaderi.archcomponents.models.repositories;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by sasannaderi on 2017-06-20.
 */

public class Resource<T> {

    public final static class Status {

        public final static int SUCCESS = 0;
        public final static int ERROR = 1;
        public final static int LOADING = 2;

    }

    @NonNull
    public final int status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    private Resource(int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(Status.LOADING, data, null);
    }

}
