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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    List<TruyenTranh> tranhList;
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
        tranhList = new ArrayList<>();
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
                    clickChapTruyen(truyenTranh,chapTruyen.getId());
                }
            });
            rvList.setAdapter(chapTruyenAdapter);
            if(truyenTranh.isFavorite()){
                btnTheoDoi.setVisibility(View.GONE);
                btnBoTheoDoi.setVisibility(View.VISIBLE);
            }else{
                btnTheoDoi.setVisibility(View.VISIBLE);
                btnBoTheoDoi.setVisibility(View.GONE);
            }
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        truyenTranh.setFavorite(true);
        String pathObject = String.valueOf(truyenTranh.getId());

        DatabaseReference reference = database.getReference("list_user/"+user.getUid()+"/list_history");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(tranhList != null){
                    tranhList.clear();
                }
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TruyenTranh truyenTranh1 = dataSnapshot.getValue(TruyenTranh.class);
                    tranhList.add(truyenTranh1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for(int i=0;i<tranhList.size();i++){
            if(truyenTranh.getId() == tranhList.get(i).getId()){
                reference.child(String.valueOf(truyenTranh.getId())).updateChildren(truyenTranh.toMap());
                break;
            }
        }
        DatabaseReference databaseReference = database.getReference("list_truyen/"+user.getUid()+"/list_truyen_tranh");
        databaseReference.child(String.valueOf(truyenTranh.getId())).updateChildren(truyenTranh.toMap());

        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_favorite");
        myRef.child(pathObject).setValue(truyenTranh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(),"Add Favorite Success",Toast.LENGTH_SHORT).show();
            }
        });

        btnTheoDoi.setVisibility(View.GONE);
        btnBoTheoDoi.setVisibility(View.VISIBLE);
    }
    private void onClickDocTruyen(TruyenTranh truyenTranh){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_history");

        String pathObject = String.valueOf(truyenTranh.getId());
        myRef.child(pathObject).setValue(truyenTranh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
        clickChapTruyen(truyenTranh,1);
    }
    private void onClickBoTheoDoi(TruyenTranh truyenTranh){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        truyenTranh.setFavorite(false);
        String pathObject = String.valueOf(truyenTranh.getId());
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = database.getReference("list_truyen/"+user.getUid()+"/list_truyen_tranh");
        databaseReference.child(pathObject).updateChildren(truyenTranh.toMap());

        DatabaseReference reference = database.getReference("list_user/"+user.getUid()+"/list_history");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(tranhList != null){
                    tranhList.clear();
                }
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TruyenTranh truyenTranh1 = dataSnapshot.getValue(TruyenTranh.class);
                    tranhList.add(truyenTranh1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for(int i=0;i<tranhList.size();i++){
            if(truyenTranh.getId() == tranhList.get(i).getId()){
                reference.child(String.valueOf(truyenTranh.getId())).updateChildren(truyenTranh.toMap());
                break;
            }
        }

        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_favorite");
        myRef.child(pathObject).removeValue();

        btnTheoDoi.setVisibility(View.VISIBLE);
        btnBoTheoDoi.setVisibility(View.GONE);
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
                Toast.makeText(getActivity(),"Fail",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void clickChapTruyen(TruyenTranh truyenTranh,int i){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_history");

        String pathObject = String.valueOf(truyenTranh.getId());
        myRef.child(pathObject).setValue(truyenTranh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
        mMainActivity.goToDocTruyenFragment(truyenTranh,i);
    }
}
