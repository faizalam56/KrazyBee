package com.example.krazybeeassignment.repository;

import android.arch.lifecycle.MutableLiveData;

import com.example.krazybeeassignment.dto.Resource;
import com.example.krazybeeassignment.network.APIClient;
import com.example.krazybeeassignment.network.APIInterface;
import com.example.krazybeeassignment.responcemodel.AlbumResponce;
import com.example.krazybeeassignment.responcemodel.AlbumResponseList;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {

    private APIInterface apiInterface;
    public AlbumRepository(){
        apiInterface = APIClient.getClient().create(APIInterface.class);
    }
    public MutableLiveData<Resource<AlbumResponseList>> callAlbumListApi(){
        final MutableLiveData<Resource<AlbumResponseList>> albumLiveData = new MutableLiveData<>();
        albumLiveData.setValue(Resource.<AlbumResponseList>loading());
        Call<JsonObject> call = apiInterface.getAlbumList();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Gson gson = new Gson();
                if(response.body()!=null){

                    /*ArrayList<AlbumResponce> yourArray = gson.fromJson(response.body().toString(), new TypeToken<List<AlbumResponce>>(){}.getType());
                    AlbumResponseList responseList = new AlbumResponseList();
                    responseList.setAlbumResponceArrayList(yourArray);*/
                    JSONArray jsonarray = null;
                    AlbumResponseList responseList = new AlbumResponseList();
                    ArrayList<AlbumResponce> responceArrayList = new ArrayList<>();
                    try {
                        jsonarray = new JSONArray(response.body().toString());
                        for (int i = 0; i < jsonarray.length(); i++) {
                            JSONObject jsonobject = jsonarray.getJSONObject(i);
                            AlbumResponce albumResponce = gson.fromJson(jsonobject.toString(),AlbumResponce.class);
                            responceArrayList.add(albumResponce);
                        }
                        responseList.setAlbumResponceArrayList(responceArrayList);
                        albumLiveData.setValue(Resource.success(responseList));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
        return albumLiveData;
    }
}
