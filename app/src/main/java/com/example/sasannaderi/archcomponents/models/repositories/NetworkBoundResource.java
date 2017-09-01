package com.example.sasannaderi.archcomponents.models.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.sasannaderi.archcomponents.api.responses.ApiResponse;

/**
 * Created by sasannaderi on 2017-06-20.
 * <p>
 * Attempts to fetch resource from local database before fetching from
 * web server. Uses LiveData to allow listening observers to receive status updates
 * and final outcome (success/failure).
 */

public abstract class NetworkBoundResource<ResultType, RequestType> {

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    NetworkBoundResource() {
        result.setValue(Resource.<ResultType>loading(null));
        final LiveData<ResultType> dbSource = loadFromDb();
        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType data) {
                result.removeSource(dbSource);
                if (shouldFetch(data)) {
                    fetchFromNetwork(dbSource);
                } else {
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            result.setValue(Resource.success(newData));
                        }
                    });
                }
            }
        });
    }

    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
        final LiveData<ApiResponse<RequestType>> apiResponse = createCall();

        result.addSource(dbSource, new Observer<ResultType>() {
            @Override
            public void onChanged(@Nullable ResultType newData) {
                result.setValue(Resource.loading(newData));
            }
        });

        result.addSource(apiResponse, new Observer<ApiResponse<RequestType>>() {
            @Override
            public void onChanged(@Nullable final ApiResponse<RequestType> response) {
                result.removeSource(dbSource);
                result.removeSource(apiResponse);

                if (response.isSuccessful()) {
                    new AsyncTask<Void, Void, Void>() {

                        @Override
                        protected Void doInBackground(Void... params) {
                            saveCallResult(processResponse(response));
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            result.addSource(loadFromDb(), new Observer<ResultType>() {
                                @Override
                                public void onChanged(@Nullable ResultType newData) {
                                    result.setValue(Resource.success(newData));
                                }
                            });
                        }
                    }.execute();
                } else {
                    onFetchFailed();
                    result.addSource(dbSource, new Observer<ResultType>() {
                        @Override
                        public void onChanged(@Nullable ResultType newData) {
                            result.setValue(Resource.error(response.getErrorMessage(), newData));
                        }
                    });
                }
            }
        });
    }

    protected void onFetchFailed() {
        // Override
    }

    public LiveData<Resource<ResultType>> asLiveData() {
        return result;
    }

    protected RequestType processResponse(ApiResponse<RequestType> response) {
        return response.getBody();
    }

    protected abstract void saveCallResult(@NonNull RequestType item);

    protected abstract boolean shouldFetch(@Nullable ResultType data);

    @NonNull
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    protected abstract LiveData<ApiResponse<RequestType>> createCall();
}
