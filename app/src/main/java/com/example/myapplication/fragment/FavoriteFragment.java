package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.TruyenTranhAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {
    public static final String TAG = FavoriteFragment.class.getName();
    View mView;
    RecyclerView rvList;
    TruyenTranhAdapter truyenTranhAdapter;
    List<TruyenTranh> list;
    MainActivity mMainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_favorite,container,false);

        rvList =mView.findViewById(R.id.rvList);

        mMainActivity = (MainActivity) getActivity();
        list = new ArrayList<>();

//        TruyenTranh truyenTranh = getArguments()

//        list.add(bundle));

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),3);
        rvList.setLayoutManager(layoutManager);



        truyenTranhAdapter = new TruyenTranhAdapter(list, getContext(), new TruyenTranhAdapter.IClickListener() {
            @Override
            public void onClickItemTruyenTranh(TruyenTranh truyenTranh) {
                mMainActivity.goToTruyenTranhFragment(truyenTranh);
            }
        });

        rvList.setAdapter(truyenTranhAdapter);

        return mView;
    }
}
