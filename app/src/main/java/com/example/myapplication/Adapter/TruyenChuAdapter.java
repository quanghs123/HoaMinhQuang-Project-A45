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
import com.example.myapplication.Module.TruyenChu;
import com.example.myapplication.R;

import java.util.List;

public class TruyenChuAdapter extends RecyclerView.Adapter<TruyenChuAdapter.Viewholder> {
    List<TruyenChu> list;
    Context mContext;
    IClickListener mIOnClickTruyenChu;

    public interface IClickListener{
        void onClickItemTruyenChu(TruyenChu truyenChu);
    }

    public TruyenChuAdapter(List<TruyenChu> list, Context mContext, IClickListener mIOnClickTruyenChu) {
        this.list = list;
        this.mContext = mContext;
        this.mIOnClickTruyenChu = mIOnClickTruyenChu;
    }


    @NonNull
    @Override
    public TruyenChuAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.icon_truyentranh,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull TruyenChuAdapter.Viewholder holder, int position) {
        TruyenChu truyenChu = list.get(position);

        holder.tvTenTruyen.setText(truyenChu.getTenTruyen());

        holder.tvTenChap.setText(truyenChu.getTenChap());

        holder.tvCapNhat.setText(truyenChu.getCapNhat());

        Glide.with(this.mContext).load(truyenChu.getLinkAnh()).into(holder.imgTruyenChu);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIOnClickTruyenChu.onClickItemTruyenChu(truyenChu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tvTenTruyen,tvTenChap,tvCapNhat;
        ImageView imgTruyenChu;
        LinearLayout linearLayout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            tvTenTruyen = itemView.findViewById(R.id.tvTenTruyen);
            tvTenChap = itemView.findViewById(R.id.tvTenChap);
            tvCapNhat = itemView.findViewById(R.id.tvCapNhat);
            imgTruyenChu = itemView.findViewById(R.id.imgTruyenTranh);
            linearLayout = itemView.findViewById(R.id.lyTruyenTranh);
        }
    }
}
