package com.example.sasannaderi.archcomponents.api.responses;

import android.support.annotation.Nullable;

import java.io.IOException;

import retrofit2.Response;

/**
 * Created by sasannaderi on 2017-06-20.
 */

public class ApiResponse<T> {

    private final int mCode;
    @Nullable
    public final T mBody;
    @Nullable
    public final String mErrorMessage;

    public ApiResponse(Throwable error) {
        mCode = 500;
        mBody = null;
        mErrorMessage = error.getMessage();
    }

    public ApiResponse(Response<T> response) {
        mCode = response.code();
        if (response.isSuccessful()) {
            mBody = response.body();
            mErrorMessage = null;
        } else {
            String message = null;
            if (response.errorBody() != null) {
                try {
                    message = response.errorBody().string();
                } catch (IOException ignored) {
                }
            }
            if (message == null || message.trim().length() == 0) {
                message = response.message();
            }
            mErrorMessage = message;
            mBody = null;
        }
    }

    public boolean isSuccessful() {
        return mCode >= 200 && mCode < 300;
    }

    public int getCode() {
        return mCode;
    }

    public T getBody() {
        return mBody;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

}
