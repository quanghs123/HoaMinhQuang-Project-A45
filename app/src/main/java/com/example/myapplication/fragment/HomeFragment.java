package com.example.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapplication.API.APIClient;
import com.example.myapplication.Adapter.ImageAdapter;
import com.example.myapplication.Adapter.TruyenTranhAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.Image;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getName();

    ViewPager viewPager;
    CircleIndicator circleIndicator;
    List<Image> imageList,list;
    ImageAdapter imageAdapter;
    Timer mTimer;
    RecyclerView rvList;
    TruyenTranhAdapter truyenTranhAdapter;
    List<TruyenTranh> truyenTranhList;
    View mView;
    MainActivity mMainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home,container,false);
        viewPager = mView.findViewById(R.id.viewPager);
        circleIndicator = mView.findViewById(R.id.circle_indicator);
        rvList = mView.findViewById(R.id.rvList);

        truyenTranhList = new ArrayList<>();
        imageList = new ArrayList<>();

        mMainActivity = (MainActivity) getActivity();

        getListTruyen();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),3);
        rvList.setLayoutManager(layoutManager);



        return mView;
    }
    private void getListTruyen() {
        Call<List<TruyenTranh>> call = APIClient.getInstance().getListTruyenTranh();
        call.enqueue(new Callback<List<TruyenTranh>>() {
            @Override
            public void onResponse(Call<List<TruyenTranh>> call, Response<List<TruyenTranh>> response) {
                truyenTranhList = response.body();
                mMainActivity.setList1(truyenTranhList);
                truyenTranhAdapter = new TruyenTranhAdapter(truyenTranhList, getContext(), new TruyenTranhAdapter.IClickListener() {
                    @Override
                    public void onClickItemTruyenTranh(TruyenTranh truyenTranh) {
                        mMainActivity.goToTruyenTranhFragment(truyenTranh);
                    }
                });
                rvList.setAdapter(truyenTranhAdapter);


                for(int i=0;i<4;i++){
                    imageList.add(new Image(truyenTranhList.get(i).getLinkAnh()));
                }
                imageAdapter = new ImageAdapter(getContext(),imageList);
                viewPager.setAdapter(imageAdapter);
                circleIndicator.setViewPager(viewPager);
                imageAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
                list = imageList;
                autoSlideImage();
            }

            @Override
            public void onFailure(Call<List<TruyenTranh>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void autoSlideImage(){
        if(list==null||list.isEmpty()||viewPager==null){
            return;
        }
        //Init timer
        if(mTimer==null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = list.size()-1;
                        if(currentItem<totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        }else{
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 300,3000);
    }
}
