package com.example.krazybeeassignment.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {
    @GET("albums")
    Call<JsonObject> getAlbumList();

}
