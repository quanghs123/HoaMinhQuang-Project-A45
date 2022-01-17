package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.TruyenTranhAdapter;
import com.example.myapplication.MainActivity;
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

public class FavoriteFragment extends Fragment {
    public static final String TAG = FavoriteFragment.class.getName();
    View mView;
    TruyenTranh truyenTranh;
    List<TruyenTranh> list;
    RecyclerView rvList;
    TruyenTranhAdapter truyenTranhAdapter;
    MainActivity mMainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favorite, container, false);

        list = new ArrayList<>();
        rvList = mView.findViewById(R.id.rvList);
        mMainActivity = (MainActivity) getActivity();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rvList.setLayoutManager(layoutManager);
        getListFavoriteFromRealTimeDatabase();
        truyenTranhAdapter = new TruyenTranhAdapter(list, getContext(), new TruyenTranhAdapter.IClickListener() {
            @Override
            public void onClickItemTruyenTranh(TruyenTranh truyenTranh) {
                mMainActivity.goToTruyenTranhFragment(truyenTranh);
            }
        });
        rvList.setAdapter(truyenTranhAdapter);

        return mView;
    }
    private void getListFavoriteFromRealTimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_Favorite");

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
}
