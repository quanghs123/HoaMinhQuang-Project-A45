package com.example.myapplication.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.MainActivity;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.R;

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

            }
        });

        btnTheoDoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.sendDataToFavoriteFragment(truyenTranh);
            }
        });

        return mView;
    }
}
