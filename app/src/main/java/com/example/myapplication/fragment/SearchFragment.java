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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    public static final String TAG = SearchFragment.class.getName();

    View mView;
    List<TruyenTranh> truyenTranhList;
    List<TruyenChu> truyenChuList;
    TruyenTranhAdapter truyenTranhAdapter;
    TruyenChuAdapter truyenChuAdapter;
    RecyclerView rvList;
    MainActivity mMainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search,container,false);

        rvList = mView.findViewById(R.id.rvList);
        mMainActivity = (MainActivity) getActivity();
        if(CHECK){
            truyenTranhList = new ArrayList<>();
            truyenTranhList = (List<TruyenTranh>) getArguments().get("list_truyen");
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
            rvList.setLayoutManager(layoutManager);
            truyenTranhAdapter = new TruyenTranhAdapter(truyenTranhList, getContext(), new TruyenTranhAdapter.IClickListener() {
                @Override
                public void onClickItemTruyenTranh(TruyenTranh truyenTranh) {
                    mMainActivity.goToTruyenTranhFragment(truyenTranh);
                }
            });
            rvList.setAdapter(truyenTranhAdapter);
        }
        else{
            truyenChuList = new ArrayList<>();
            truyenChuList = (List<TruyenChu>) getArguments().get("list_truyen_chu");
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
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

}
