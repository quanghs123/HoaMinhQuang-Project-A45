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

import com.bumptech.glide.Glide;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TruyenTranhFragment extends Fragment {
    public static final String TAG = TruyenTranhFragment.class.getName();
    TextView tvTenTruyen,tvCapNhat,tvTacGia,tvTinhTrang,tvTheLoai,tvNoiDung;
    ImageView imgManga;
    RelativeLayout btnDocTruyen, btnTheoDoi;
    MainActivity mMainActivity;
    TruyenTranh truyenTranh;
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
        }

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
    }
    private void onClickDocTruyen(TruyenTranh truyenTranh){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("List_History");

        String pathObject = String.valueOf(truyenTranh.getId());
        myRef.child(pathObject).setValue(truyenTranh, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
    }
}
