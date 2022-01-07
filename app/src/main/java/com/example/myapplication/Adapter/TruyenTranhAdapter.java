package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Module.TruyenTranh;
import com.example.myapplication.R;

import java.util.List;


public class TruyenTranhAdapter extends RecyclerView.Adapter<TruyenTranhAdapter.Viewoder> {
    List<TruyenTranh> truyenTranhList;
    Context mContext;
    IClickListener mIOnClickTruyenTranh;

    public interface IClickListener{
        void onClickItemTruyenTranh(TruyenTranh truyenTranh);
    }
    public TruyenTranhAdapter(List<TruyenTranh> truyenTranhList, Context mContext, IClickListener iOnClickTruyenTranh) {
        this.truyenTranhList = truyenTranhList;
        this.mIOnClickTruyenTranh = iOnClickTruyenTranh;
        this.mContext = mContext;
    }
    @NonNull
    @Override
    public Viewoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.icon_truyentranh,parent,false);
        Viewoder viewoder = new Viewoder(view);
        return viewoder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewoder holder, int position) {
        TruyenTranh truyenTranh = truyenTranhList.get(position);

        holder.tvTenTruyen.setText(truyenTranh.getTenTruyen());

        holder.tvTenChap.setText(truyenTranh.getTenChap());

        holder.tvCapNhat.setText(truyenTranh.getCapNhat());

        Glide.with(this.mContext).load(truyenTranh.getLinkAnh()).into(holder.imgTruyenTranh);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIOnClickTruyenTranh.onClickItemTruyenTranh(truyenTranh);
            }
        });
    }

    @Override
    public int getItemCount() {
        return truyenTranhList.size();
    }
    public class Viewoder extends RecyclerView.ViewHolder {
        TextView tvTenTruyen,tvTenChap,tvCapNhat;
        ImageView imgTruyenTranh;
        LinearLayout linearLayout;
        public Viewoder(@NonNull View itemView) {
            super(itemView);
            tvTenTruyen = itemView.findViewById(R.id.tvTenTruyen);
            tvTenChap = itemView.findViewById(R.id.tvTenChap);
            tvCapNhat = itemView.findViewById(R.id.tvCapNhat);
            imgTruyenTranh = itemView.findViewById(R.id.imgTruyenTranh);
            linearLayout = itemView.findViewById(R.id.lyTruyenTranh);
        }
    }
}
