package com.example.myapplication.fragment;

import static com.example.myapplication.MainActivity.CHECK;

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

import com.example.myapplication.Adapter.ImageAdapter;
import com.example.myapplication.Adapter.TruyenChuAdapter;
import com.example.myapplication.Adapter.TruyenTranhAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.Image;
import com.example.myapplication.Module.TruyenChu;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    RecyclerView rvList;
    TruyenTranhAdapter truyenTranhAdapter;
    TruyenChuAdapter truyenChuAdapter;
    List<TruyenChu> truyenChuList;
    List<TruyenTranh> truyenTranhList;
    List<TruyenTranh> tranhList;
    View mView;
    MainActivity mMainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home,container,false);
        viewPager = mView.findViewById(R.id.viewPager);
        circleIndicator = mView.findViewById(R.id.circle_indicator);
        rvList = mView.findViewById(R.id.rvList);
        mMainActivity = (MainActivity) getActivity();
        list = new ArrayList<>();
        if(CHECK){
            truyenTranhList = new ArrayList<>();
            tranhList = new ArrayList<>();
            getListTruyenFromRealTimeDatabase();
            setFavorite();
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),3);
            rvList.setLayoutManager(layoutManager);
            truyenTranhAdapter = new TruyenTranhAdapter(truyenTranhList, getContext(), new TruyenTranhAdapter.IClickListener() {
                @Override
                public void onClickItemTruyenTranh(TruyenTranh truyenTranh) {
                    mMainActivity.goToTruyenTranhFragment(truyenTranh);
                }
            });
            rvList.setAdapter(truyenTranhAdapter);
            mMainActivity.setList1(truyenTranhList);
        }
        else{
            truyenChuList = new ArrayList<>();
            getListTruyenChu();
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),3);
            rvList.setLayoutManager(layoutManager);
            truyenChuAdapter = new TruyenChuAdapter(truyenChuList, getContext(), new TruyenChuAdapter.IClickListener() {
                @Override
                public void onClickItemTruyenChu(TruyenChu truyenChu) {
                    mMainActivity.goToTruyenChuFragment(truyenChu);
                }
            });
            rvList.setAdapter(truyenChuAdapter);
            mMainActivity.setList2(truyenChuList);
        }


        imageAdapter = new ImageAdapter(getActivity(),list);
        viewPager.setAdapter(imageAdapter);
        circleIndicator.setViewPager(viewPager);
        imageAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

//        autoSlideImage();

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
                        list.add(new Image(truyenTranh.getLinkAnh()));
                        imageAdapter.notifyDataSetChanged();
                    }
                    truyenTranhAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TruyenTranh truyenTranh = snapshot.getValue(TruyenTranh.class);
                if(truyenTranh == null ||truyenTranhList == null || truyenTranhList.isEmpty()){
                    return;
                }
                for(int i=0 ;i<truyenTranhList.size();i++){
                    if(truyenTranh.getId() == truyenTranhList.get(i).getId()){
                        truyenTranhList.set(i,truyenTranh);
                    }
                }
                truyenTranhAdapter.notifyDataSetChanged();
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
                    if(list.size()<4){
                        list.add(new Image(truyenChu.getLinkAnh()));
                        imageAdapter.notifyDataSetChanged();
                    }
                    truyenChuAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TruyenChu truyenChu = snapshot.getValue(TruyenChu.class);
                if(truyenChu == null ||truyenChuList == null || truyenChuList.isEmpty()){
                    return;
                }
                for(int i=0 ;i<truyenChuList.size();i++){
                    if(truyenChu.getId() == truyenChuList.get(i).getId()){
                        truyenChuList.set(i,truyenChu);
                    }
                }
                truyenChuAdapter.notifyDataSetChanged();
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
    private void setFavorite(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_favorite");
        DatabaseReference databaseReference = database.getReference("list_truyen");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(tranhList != null){
                    tranhList.clear();
                }
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TruyenTranh truyenTranh = dataSnapshot.getValue(TruyenTranh.class);
                    tranhList.add(truyenTranh);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for(int i=0;i<truyenTranhList.size();i++){
            for (int j=0;j<tranhList.size();j++){
                if (truyenTranhList.get(i).getId() == tranhList.get(j).getId()){
                    truyenTranhList.get(i).setFavorite(true);
                    databaseReference.child(String.valueOf(truyenTranhList.get(i).getId())).updateChildren(truyenTranhList.get(i).toMap());
                }
            }
        }
    }
}
