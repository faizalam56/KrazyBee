package com.example.krazybeeassignment.viewmodel;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.example.krazybeeassignment.dto.Resource;
import com.example.krazybeeassignment.repository.AlbumRepository;
import com.example.krazybeeassignment.responcemodel.AlbumResponce;
import com.example.krazybeeassignment.responcemodel.AlbumResponseList;

public class AlbumViewModel extends ViewModel {
    private MutableLiveData<Request> albumRequest = new MutableLiveData<>();
    private LiveData<Resource<AlbumResponseList>> albumResult = Transformations.switchMap(albumRequest, new Function<Request, LiveData<Resource<AlbumResponseList>>>() {
        @Override
        public LiveData<Resource<AlbumResponseList>> apply(Request input) {
            LiveData<Resource<AlbumResponseList>> resourceLiveData = new AlbumRepository().callAlbumListApi();
            return resourceLiveData;
        }
    });
    public LiveData<Resource<AlbumResponseList>> getAlbumData(){

        return this.albumResult;
    }

    class Request{
        public Request(){
        }
    }

    public void callAlbumListApi(){
        albumRequest.setValue(new Request());
    }
}
