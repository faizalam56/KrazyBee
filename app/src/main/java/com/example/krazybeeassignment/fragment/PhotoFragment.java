package com.example.krazybeeassignment.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.krazybeeassignment.R;

public class PhotoFragment extends Fragment {
    View view;
    public static PhotoFragment newInstance(int Id){
        PhotoFragment fragmentExamResults = new PhotoFragment();
        Bundle bundle = new Bundle();

        bundle.putInt("Id",Id);

        fragmentExamResults.setArguments(bundle);
        return fragmentExamResults;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_photo,container,false);
        return view;
    }
}
