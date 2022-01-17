package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Module.AnhTruyen;
import com.example.myapplication.R;

import java.util.List;

public class AnhTruyenAdapter extends RecyclerView.Adapter<AnhTruyenAdapter.Viewholder> {
    List<AnhTruyen> anhTruyenList;
    Context mContext;

    public AnhTruyenAdapter(List<AnhTruyen> anhTruyenList, Context mContext) {
        this.anhTruyenList = anhTruyenList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AnhTruyenAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_anh_truyen,parent,false);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnhTruyenAdapter.Viewholder holder, int position) {
        AnhTruyen anhTruyen = anhTruyenList.get(position);
        Glide.with(this.mContext).load(anhTruyen.getLinkAnh()).into(holder.imgAnhTruyen);
    }

    @Override
    public int getItemCount() {
        return anhTruyenList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView imgAnhTruyen;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imgAnhTruyen = itemView.findViewById(R.id.imgAnhTruyen);
        }
    }
}
