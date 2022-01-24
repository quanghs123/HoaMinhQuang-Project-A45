package com.example.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.AnhTruyenAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.AnhTruyen;
import com.example.myapplication.Module.ChapTruyen;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DocTruyenFragment extends Fragment {
    List<AnhTruyen> anhTruyenList;
    TruyenTranh truyenTranh;
    AnhTruyenAdapter anhTruyenAdapter;
    RecyclerView rvList;
    int idChap;
    ImageView imgBackChap,imgNextChap;
    MainActivity mMainActivity;
    View mView;

    public static DocTruyenFragment getInstance(TruyenTranh truyenTranh,int i){
        DocTruyenFragment docTruyenFragment = new DocTruyenFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_id_chap",i);
        bundle.putSerializable("object_truyen_tranh",truyenTranh);
        docTruyenFragment.setArguments(bundle);

        return docTruyenFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_doc_truyen,container,false);

        anhTruyenList = new ArrayList<>();
        mMainActivity = (MainActivity) getActivity();

        rvList = mView.findViewById(R.id.rvList);
        imgBackChap = mView.findViewById(R.id.imgBackChap);
        imgNextChap = mView.findViewById(R.id.imgNextChap);


        Bundle bundle = getArguments();
        if(bundle != null){
            idChap = (int) bundle.get("object_id_chap");
            truyenTranh = (TruyenTranh) bundle.get("object_truyen_tranh");
            getListAnh(truyenTranh.getId(),idChap);
            anhTruyenAdapter = new AnhTruyenAdapter(anhTruyenList,getActivity());
            rvList.setAdapter(anhTruyenAdapter);
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),1);
        rvList.setLayoutManager(layoutManager);

        imgBackChap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBackChap();
            }
        });

        imgNextChap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickNextChap();
            }
        });


        return mView;
    }
    private void getListAnh(int i,int j){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();

        myRef.child("list_truyen/"+i+"/chapTruyen/"+j+"/anhTruyenList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                anhTruyenList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    AnhTruyen anhTruyen = snap.getValue(AnhTruyen.class);
                    anhTruyenList.add(anhTruyen);
                }
                anhTruyenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(),"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onClickBackChap(){
        if(idChap == 1){
            Toast.makeText(getActivity(),"This is the first chapter",Toast.LENGTH_SHORT).show();
            return;
        }
        mMainActivity.goToDocTruyenFragment(truyenTranh,idChap-1);
    }
    private void onClickNextChap(){
        if(idChap == truyenTranh.getChapTruyen().size()-1){
            Toast.makeText(getActivity(),"This is the last chapter",Toast.LENGTH_SHORT).show();
            return;
        }
        mMainActivity.goToDocTruyenFragment(truyenTranh,idChap+1);
    }

}
