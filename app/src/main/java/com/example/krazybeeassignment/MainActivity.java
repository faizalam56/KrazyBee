package com.example.krazybeeassignment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.krazybeeassignment.databinding.ActivityMainBinding;
import com.example.krazybeeassignment.dto.Resource;
import com.example.krazybeeassignment.fragment.PhotoFragment;
import com.example.krazybeeassignment.responcemodel.AlbumResponce;
import com.example.krazybeeassignment.responcemodel.AlbumResponseList;
import com.example.krazybeeassignment.viewmodel.AlbumViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private View rootVew;
    private AlbumViewModel albumViewModel;

    private ViewPagerAdapter adapter;
    private  List<Fragment> mFragmentList = new ArrayList<>();
    private  List<String> mFragmentTitleList = new ArrayList<>();
    private List<AlbumResponce> albumDataList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(getLayoutInflater(),R.layout.activity_main,null,false);
        rootVew = binding.getRoot();
        setContentView(rootVew);


        albumViewModel = ViewModelProviders.of(this).get(AlbumViewModel.class);
        albumViewModel.callAlbumListApi();
        albumViewModel.getAlbumData().observe(this, new Observer<Resource<AlbumResponseList>>() {
            @Override
            public void onChanged(@Nullable Resource<AlbumResponseList> albumResponseListResource) {
                processResponse(albumResponseListResource);
            }
        });
    }

    private void processResponse(Resource resource){
        switch (resource.getStatus()){
            case LOADING:
                binding.setLoading(true);
                break;
            case SUCCESS: {
                binding.setLoading(false);
                AlbumResponseList albumResponseList = (AlbumResponseList) resource.getData();
                ArrayList<AlbumResponce> albumList=albumResponseList.getAlbumResponceArrayList();
                if(albumList!=null&&albumList.size()>0){
                    setAlbumValue(albumList);
                }
                break;
            }
            case ERROR:
                binding.setLoading(false);

                break;
        }

    }

    private void setAlbumValue(List<AlbumResponce> albumDataList){
        this.albumDataList = albumDataList;
        for(int i = 0; i< albumDataList.size(); i++){
            mFragmentList.add(PhotoFragment.newInstance(albumDataList.get(i).getId()));
            mFragmentTitleList.add(albumDataList.get(i).getTitle());
        }

        setUpViewPager();
    }

    private void setUpViewPager(){
        //binding.viewpager.setOffscreenPageLimit(albumDataList.size());
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),mFragmentList,mFragmentTitleList);
        binding.viewpager.setAdapter(adapter);
        binding.tabLayoutExam.setupWithViewPager(binding.viewpager);



    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragmentList;
        private  List<String> mFragmentTitleList;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titleLists) {
            super(fm);
            this.mFragmentList = fragments;
            this.mFragmentTitleList = titleLists;
        }

        @Override
        public Fragment getItem(int i) {
            return mFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int i) {
            return mFragmentTitleList.get(i);
        }
    }
}
