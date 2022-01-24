package com.example.myapplication.fragment;

import static com.example.myapplication.MainActivity.CHECK;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.TruyenChuAdapter;
import com.example.myapplication.Adapter.TruyenTranhAdapter;
import com.example.myapplication.MainActivity;
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

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    public static final String TAG = FavoriteFragment.class.getName();
    View mView;
    List<TruyenTranh> list;
    RecyclerView rvList;
    TruyenTranhAdapter truyenTranhAdapter;
    TruyenChuAdapter truyenChuAdapter;
    List<TruyenChu> truyenChuList;
    MainActivity mMainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);

        rvList = mView.findViewById(R.id.rvList);
        mMainActivity = (MainActivity) getActivity();

        if(CHECK){
            list = new ArrayList<>();
            getListFavoriteFromRealTimeDatabase();
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
            rvList.setLayoutManager(layoutManager);
            truyenTranhAdapter = new TruyenTranhAdapter(list, getContext(), new TruyenTranhAdapter.IClickListener() {
                @Override
                public void onClickItemTruyenTranh(TruyenTranh truyenTranh) {
                    mMainActivity.goToTruyenTranhFragment(truyenTranh);
                }
            });
            rvList.setAdapter(truyenTranhAdapter);
        }
        else{
            truyenChuList = new ArrayList<>();
            getListTruyenChu();
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
            rvList.setLayoutManager(layoutManager);
            truyenChuAdapter = new TruyenChuAdapter(truyenChuList, getContext(), new TruyenChuAdapter.IClickListener() {
                @Override
                public void onClickItemTruyenChu(TruyenChu truyenChu) {
                    mMainActivity.goToTruyenChuFragment(truyenChu);
                }
            });
            rvList.setAdapter(truyenChuAdapter);
        }
        return mView;
    }
    private void getListFavoriteFromRealTimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_favorite");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                TruyenTranh truyenTranh = snapshot.getValue(TruyenTranh.class);
                if(truyenTranh!=null){
                    list.add(truyenTranh);
                    truyenTranhAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                TruyenTranh truyenTranh = snapshot.getValue(TruyenTranh.class);
                if(truyenTranh == null || list == null || list.isEmpty()){
                    return;
                }
                for(int i = 0;i<list.size();i++){
                    if(truyenTranh.getId() == list.get(i).getId()){
                        list.remove(list.get(i));
                        break;
                    }
                }
                truyenTranhAdapter.notifyDataSetChanged();
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_truyen_chu_favorite");
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
                TruyenChu truyenChu = snapshot.getValue(TruyenChu.class);
                if(truyenChu == null || truyenChuList == null || truyenChuList.isEmpty()){
                    return;
                }
                for(int i = 0;i<truyenChuList.size();i++){
                    if(truyenChu.getId() == truyenChuList.get(i).getId()){
                        truyenChuList.remove(truyenChuList.get(i));
                        break;
                    }
                }
                truyenChuAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
