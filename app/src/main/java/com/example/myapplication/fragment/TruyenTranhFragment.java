package com.example.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Adapter.ChapTruyenAdapter;
import com.example.myapplication.MainActivity;
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

public class TruyenTranhFragment extends Fragment {
    public static final String TAG = TruyenTranhFragment.class.getName();
    TextView tvTenTruyen,tvCapNhat,tvTacGia,tvTinhTrang,tvTheLoai,tvNoiDung;
    ImageView imgManga;
    RelativeLayout btnDocTruyen, btnTheoDoi,btnBoTheoDoi;
    MainActivity mMainActivity;
    TruyenTranh truyenTranh;
    RecyclerView rvList;
    ChapTruyenAdapter chapTruyenAdapter;
    List<ChapTruyen> chapTruyenList;
    View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_truyen_tranh,container,false);
        tvTenTruyen = mView.findViewById(R.id.tvTenTruyen);
        tvCapNhat = mView.findViewById(R.id.tvCapNhat);
        tvTacGia = mView.findViewById(R.id.tvTacGia);
        tvTinhTrang = mView.findViewById(R.id.tvTinhTrang);
        tvTheLoai = mView.findViewById(R.id.tvTheLoai);
        tvNoiDung = mView.findViewById(R.id.tvNoiDung);
        imgManga = mView.findViewById(R.id.imgManga);
        btnDocTruyen = mView.findViewById(R.id.btnDocTruyen);
        btnTheoDoi = mView.findViewById(R.id.btnTheoDoi);
        btnBoTheoDoi = mView.findViewById(R.id.btnBoTheoDoi);

        chapTruyenList = new ArrayList<>();

        rvList = mView.findViewById(R.id.rvList);
        mMainActivity = (MainActivity) getActivity();

        Bundle bundle = getArguments();
        if(bundle != null){
            truyenTranh = (TruyenTranh) bundle.get("object_manga");
            tvTenTruyen.setText(truyenTranh.getTenTruyen());
            tvCapNhat.setText(truyenTranh.getCapNhat());
            tvTacGia.setText(truyenTranh.getTacGia());
            tvTinhTrang.setText(truyenTranh.getTinhTrang());
            tvTheLoai.setText(truyenTranh.getTheLoai());
            tvNoiDung.setText(truyenTranh.getNoiDung());
            Glide.with(this).load(truyenTranh.getLinkAnh()).into(imgManga);
            getListChap(truyenTranh.getId());
            chapTruyenAdapter = new ChapTruyenAdapter(chapTruyenList, new ChapTruyenAdapter.IClickListener() {
                @Override
                public void onClickItemChapTruyen(ChapTruyen chapTruyen) {
                    clickChapTruyen(truyenTranh,chapTruyen);
                }
            });
            rvList.setAdapter(chapTruyenAdapter);
        }

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this.getContext(),1);
        rvList.setLayoutManager(layoutManager);

        btnDocTruyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickDocTruyen(truyenTranh);
            }
        });

        btnTheoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTheoDoi(truyenTranh);
            }
        });
        btnBoTheoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBoTheoDoi(truyenTranh);
            }
        });



        return mView;
    }
    private void onClickTheoDoi(TruyenTranh truyenTranh){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_Favorite");

        String pathObject = String.valueOf(truyenTranh.getId());
        myRef.child(pathObject).setValue(truyenTranh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity()    ,"Add Favorite Success",Toast.LENGTH_SHORT).show();
            }
        });
        btnTheoDoi.setVisibility(View.GONE);
        btnBoTheoDoi.setVisibility(View.VISIBLE);
    }
    private void onClickDocTruyen(TruyenTranh truyenTranh){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_truyen");

        String pathObject = String.valueOf(truyenTranh.getId());
        myRef.child(pathObject).setValue(truyenTranh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
    }
    private void onClickBoTheoDoi(TruyenTranh truyenTranh){
        btnTheoDoi.setVisibility(View.VISIBLE);
        btnBoTheoDoi.setVisibility(View.GONE);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_Favorite");
        myRef.child(String.valueOf(truyenTranh.getId())).removeValue();
    }
    private void getListChap(int i){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();

        myRef.child("list_truyen/"+i+"/chapTruyen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chapTruyenList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    ChapTruyen chapTruyen = snap.getValue(ChapTruyen.class);
                    chapTruyenList.add(chapTruyen);
                }
                chapTruyenAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void clickChapTruyen(TruyenTranh truyenTranh,ChapTruyen chapTruyen){
        mMainActivity.goToDocTruyenFragment(truyenTranh,chapTruyen);
    }
}
