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

public class SearchFragment extends Fragment {
    public static final String TAG = SearchFragment.class.getName();

    View mView;
    List<TruyenTranh> list;
    TruyenTranhAdapter truyenTranhAdapter;
    RecyclerView rvList;
    MainActivity mMainActivity;

    public static SearchFragment getInstance(List<TruyenTranh> truyenTranhList){
        SearchFragment searchFragment = new SearchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list_truyen", (Serializable) truyenTranhList);
        searchFragment.setArguments(bundle);

        return searchFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_search,container,false);

        rvList = mView.findViewById(R.id.rvList);
        list = new ArrayList<>();
        list = (List<TruyenTranh>) getArguments().get("list_truyen");
        mMainActivity = (MainActivity) getActivity();

        truyenTranhAdapter = new TruyenTranhAdapter(list, getContext(), new TruyenTranhAdapter.IClickListener() {
            @Override
            public void onClickItemTruyenTranh(TruyenTranh truyenTranh) {
                mMainActivity.goToTruyenTranhFragment(truyenTranh);
            }
        });
        rvList.setAdapter(truyenTranhAdapter);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),3);
        rvList.setLayoutManager(layoutManager);


        return mView;
    }

}
