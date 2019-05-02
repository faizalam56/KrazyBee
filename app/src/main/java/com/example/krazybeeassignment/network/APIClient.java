package com.example.krazybeeassignment.network;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static final int CLIENT_READ_TIME_OUT = 60;
    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(CLIENT_READ_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(CLIENT_READ_TIME_OUT,TimeUnit.SECONDS)
                .writeTimeout(CLIENT_READ_TIME_OUT,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(httpClient)
                .build();

        return retrofit;
    }
}
