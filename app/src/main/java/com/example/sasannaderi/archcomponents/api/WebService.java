package com.example.sasannaderi.archcomponents.api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sasannaderi on 2017-06-19.
 */

public class WebService {

    public static class Generator {

        private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

        private static Retrofit.Builder mBuilder =
                new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(new LiveDataCallAdapterFactory());

        private static Retrofit mRetrofit = mBuilder.build();

        private static OkHttpClient mHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        final Request original = chain.request();

                        // Add API key to each request as a custom header
                        Request request = original.newBuilder()
                                .header("API-KEY", "")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                }).build();

        public static <S> S createService(
                Class<S> serviceClass) {
            return mRetrofit.create(serviceClass);
        }
    }

}
