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

import com.example.myapplication.Adapter.AnhTruyenAdapter;
import com.example.myapplication.Module.AnhTruyen;
import com.example.myapplication.Module.ChapTruyen;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DocTruyenFragment extends Fragment {
    List<AnhTruyen> list;
    ChapTruyen chapTruyen;
    TruyenTranh truyenTranh;
    AnhTruyenAdapter anhTruyenAdapter;
    RecyclerView rvList;
    View mView;

    public static DocTruyenFragment getInstance(TruyenTranh truyenTranh,ChapTruyen chapTruyen){
        DocTruyenFragment docTruyenFragment = new DocTruyenFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_chap_truyen",chapTruyen);
        bundle.putSerializable("object_truyen_tranh",truyenTranh);
        docTruyenFragment.setArguments(bundle);

        return docTruyenFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_doc_truyen,container,false);

        initUi();
        Bundle bundle = getArguments();
        if(bundle != null){
            chapTruyen = (ChapTruyen) bundle.get("object_chap_truyen");
            truyenTranh = (TruyenTranh) bundle.get("object_truyen_tranh");
            list = chapTruyen.getAnhTruyenList();
            getListAnh(truyenTranh.getId(),chapTruyen.getId());
            anhTruyenAdapter = new AnhTruyenAdapter(list,getContext());
            rvList.setAdapter(anhTruyenAdapter);
        }

        return mView;
    }
    private void initUi(){
        rvList = mView.findViewById(R.id.rvList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),1);
        rvList.setLayoutManager(layoutManager);
    }
    private void getListAnh(int i,int j){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();

        myRef.child("list_truyen/"+i+"/chapTruyen/"+j+"/anhTruyenList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    AnhTruyen anhTruyen = snap.getValue(AnhTruyen.class);
                    list.add(anhTruyen);
                }
                anhTruyenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
