package com.example.myapplication.fragment;

import android.os.Bundle;
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
import com.example.myapplication.Adapter.ChapTruyenChuAdapter;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.ChapTruyenChu;
import com.example.myapplication.Module.TruyenChu;
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

public class TruyenChuFragment extends Fragment {
    public static final String TAG = TruyenChuFragment.class.getName();
    TextView tvTenTruyen,tvCapNhat,tvTacGia,tvTinhTrang,tvTheLoai,tvNoiDung;
    ImageView imgManga;
    RelativeLayout btnDocTruyen, btnTheoDoi,btnBoTheoDoi;
    MainActivity mMainActivity;
    TruyenChu truyenChu;
    RecyclerView rvList;
    ChapTruyenChuAdapter chapTruyenAdapter;
    List<ChapTruyenChu> chapTruyenList;
    List<TruyenChu> truyenChuList;
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
        truyenChuList = new ArrayList<>();

        rvList = mView.findViewById(R.id.rvList);
        mMainActivity = (MainActivity) getActivity();

        Bundle bundle = getArguments();
        if(bundle != null){
            truyenChu = (TruyenChu) bundle.get("object_manga");
            tvTenTruyen.setText(truyenChu.getTenTruyen());
            tvCapNhat.setText(truyenChu.getCapNhat());
            tvTacGia.setText(truyenChu.getTacGia());
            tvTinhTrang.setText(truyenChu.getTinhTrang());
            tvTheLoai.setText(truyenChu.getTheLoai());
            tvNoiDung.setText(truyenChu.getNoiDung());
            Glide.with(this).load(truyenChu.getLinkAnh()).into(imgManga);
            getListChap(truyenChu.getId());
            chapTruyenAdapter = new ChapTruyenChuAdapter(chapTruyenList, new ChapTruyenChuAdapter.IClickListener() {
                @Override
                public void onClickItemChapTruyen(ChapTruyenChu chapTruyen) {
                    clickChapTruyen(truyenChu,chapTruyen.getId());
                }
            });
            rvList.setAdapter(chapTruyenAdapter);
            if(truyenChu.isFavorite()){
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
                onClickDocTruyen(truyenChu);
            }
        });

        btnTheoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTheoDoi(truyenChu);
            }
        });
        btnBoTheoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBoTheoDoi(truyenChu);
            }
        });



        return mView;
    }
    private void onClickTheoDoi(TruyenChu truyenChu){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        truyenChu.setFavorite(true);
        String pathObject = String.valueOf(truyenChu.getId());

        DatabaseReference databaseReference = database.getReference("list_user/"+user.getUid()+"/list_truyen_chu");
        databaseReference.child(String.valueOf(truyenChu.getId())).updateChildren(truyenChu.toMap());

        DatabaseReference reference = database.getReference("list_user/"+user.getUid()+"/list_truyen_chu_history");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(truyenChuList != null){
                    truyenChuList.clear();
                }
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TruyenChu truyenChu1 = dataSnapshot.getValue(TruyenChu.class);
                    truyenChuList.add(truyenChu1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for(int i=0;i<truyenChuList.size();i++){
            if(truyenChu.getId() == truyenChuList.get(i).getId()){
                reference.child(String.valueOf(truyenChu.getId())).updateChildren(truyenChu.toMap());
                break;
            }
        }

        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_truyen_chu_favorite");
        myRef.child(pathObject).setValue(truyenChu, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(getActivity(),"Add Favorite Success",Toast.LENGTH_SHORT).show();
            }
        });
        btnTheoDoi.setVisibility(View.GONE);
        btnBoTheoDoi.setVisibility(View.VISIBLE);
    }
    private void onClickDocTruyen(TruyenChu truyenChu){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_truyen_chu_history");

        String pathObject = String.valueOf(truyenChu.getId());
        myRef.child(pathObject).setValue(truyenChu, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
        clickChapTruyen(truyenChu,1);
    }
    private void onClickBoTheoDoi(TruyenChu truyenChu){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        truyenChu.setFavorite(false);
        String pathObject = String.valueOf(truyenChu.getId());

        DatabaseReference databaseReference = database.getReference("list_user/"+user.getUid()+"/list_truyen_chu");
        databaseReference.child(pathObject).updateChildren(truyenChu.toMap());

        DatabaseReference reference = database.getReference("list_user/"+user.getUid()+"/list_truyen_chu_history");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(truyenChuList != null){
                    truyenChuList.clear();
                }
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TruyenChu truyenChu1 = dataSnapshot.getValue(TruyenChu.class);
                    truyenChuList.add(truyenChu1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        for(int i=0;i<truyenChuList.size();i++){
            if(truyenChu.getId() == truyenChuList.get(i).getId()){
                reference.child(String.valueOf(truyenChu.getId())).updateChildren(truyenChu.toMap());
                break;
            }
        }

        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_truyen_chu_favorite");
        myRef.child(pathObject).removeValue();

        btnTheoDoi.setVisibility(View.VISIBLE);
        btnBoTheoDoi.setVisibility(View.GONE);
    }
    private void getListChap(int i){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference();

        myRef.child("list_truyen_chu/"+i+"/chapTruyen").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chapTruyenList.clear();
                for(DataSnapshot snap : snapshot.getChildren()){
                    ChapTruyenChu chapTruyen = snap.getValue(ChapTruyenChu.class);
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
    private void clickChapTruyen(TruyenChu truyenChu,int i){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null){
            return;
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_user/"+user.getUid()+"/list_truyen_chu_history");

        String pathObject = String.valueOf(truyenChu.getId());
        myRef.child(pathObject).setValue(truyenChu, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
        mMainActivity.goToDocTruyenChuFragment(truyenChu,i);
    }
}
