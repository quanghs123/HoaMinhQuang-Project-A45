package com.example.myapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
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
import com.example.myapplication.Adapter.TruyenChuAdapter;
import com.example.myapplication.Adapter.TruyenTranhAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.Image;
import com.example.myapplication.Module.TruyenChu;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    List<Image> list;
    ImageAdapter imageAdapter;
    Timer mTimer;
    RecyclerView rvList,rvList1;
    TruyenTranhAdapter truyenTranhAdapter;
    TruyenChuAdapter truyenChuAdapter;
    List<TruyenChu> truyenChuList;
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
        rvList1 = mView.findViewById(R.id.rvList1);

        truyenTranhList = new ArrayList<>();
        truyenChuList = new ArrayList<>();

        list = new ArrayList<>();

        mMainActivity = (MainActivity) getActivity();

//        getListTruyen();
        getListTruyenFromRealTimeDatabase();
        getListTruyenChu();

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),3);
        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(this.getContext(),3);
        rvList.setLayoutManager(layoutManager);
        rvList1.setLayoutManager(layoutManager1);

        truyenTranhAdapter = new TruyenTranhAdapter(truyenTranhList, getContext(), new TruyenTranhAdapter.IClickListener() {
            @Override
            public void onClickItemTruyenTranh(TruyenTranh truyenTranh) {
                mMainActivity.goToTruyenTranhFragment(truyenTranh);
            }
        });
        rvList.setAdapter(truyenTranhAdapter);

        truyenChuAdapter = new TruyenChuAdapter(truyenChuList, getContext(), new TruyenChuAdapter.IClickListener() {
            @Override
            public void onClickItemTruyenChu(TruyenChu truyenChu) {
                mMainActivity.goToTruyenChuFragment(truyenChu);
            }
        });
        rvList1.setAdapter(truyenChuAdapter);

        mMainActivity.setList1(truyenTranhList);

        imageAdapter = new ImageAdapter(getActivity(),list);
        viewPager.setAdapter(imageAdapter);
        circleIndicator.setViewPager(viewPager);
        imageAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        autoSlideImage();

        return mView;
    }
    private void getListTruyenFromRealTimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_truyen");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TruyenTranh truyenTranh = snapshot.getValue(TruyenTranh.class);
                if(truyenTranh!=null){
                    truyenTranhList.add(truyenTranh);
                    if(list.size()<4){
                        getListImage(new Image(truyenTranh.getLinkAnh()));
                    }
                    truyenTranhAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getListTruyenChu(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_truyen_chu");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TruyenChu truyenChu = snapshot.getValue(TruyenChu.class);
                if(truyenChu!=null){
                    truyenChuList.add(truyenChu);
                    truyenChuAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getListTruyen() {
        Call<List<TruyenTranh>> call = APIClient.getInstance().getListTruyenTranh();
        call.enqueue(new Callback<List<TruyenTranh>>() {
            @Override
            public void onResponse(Call<List<TruyenTranh>> call, Response<List<TruyenTranh>> response) {
                truyenTranhList = response.body();
                mMainActivity.setList1(truyenTranhList);
                truyenTranhAdapter = new TruyenTranhAdapter(truyenTranhList, getActivity(), new TruyenTranhAdapter.IClickListener() {
                    @Override
                    public void onClickItemTruyenTranh(TruyenTranh truyenTranh) {
                        mMainActivity.goToTruyenTranhFragment(truyenTranh);
                    }
                });
                rvList.setAdapter(truyenTranhAdapter);


                for(int i=0;i<4;i++){
                    list.add(new Image(truyenTranhList.get(i).getLinkAnh()));
                }
                imageAdapter = new ImageAdapter(getContext(),list);
                viewPager.setAdapter(imageAdapter);
                circleIndicator.setViewPager(viewPager);
                imageAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
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
    private void getListImage(Image mImage){
        list.add(mImage);
        imageAdapter.notifyDataSetChanged();
    }
}
