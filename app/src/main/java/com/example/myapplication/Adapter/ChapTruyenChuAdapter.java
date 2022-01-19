package com.example.myapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Module.ChapTruyenChu;
import com.example.myapplication.R;

import java.util.List;

public class ChapTruyenChuAdapter extends RecyclerView.Adapter<ChapTruyenChuAdapter.Vieworder> {
    List<ChapTruyenChu> list;
    IClickListener mIClickListener;

    public interface IClickListener{
        void onClickItemChapTruyen(ChapTruyenChu chapTruyen);
    }
    public ChapTruyenChuAdapter(List<ChapTruyenChu> list, IClickListener iClickListener) {
        this.list = list;
        this.mIClickListener = iClickListener;
    }

    @NonNull
    @Override
    public ChapTruyenChuAdapter.Vieworder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_chap,parent,false);
        Vieworder vieworder = new Vieworder(view);
        return vieworder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChapTruyenChuAdapter.Vieworder holder, int position) {
        ChapTruyenChu chapTruyen = list.get(position);

        holder.tvTenChap.setText(chapTruyen.getTenChap());

        holder.tvTenChap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickListener.onClickItemChapTruyen(chapTruyen);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Vieworder extends RecyclerView.ViewHolder {
        TextView tvTenChap;
        public Vieworder(@NonNull View itemView) {
            super(itemView);
            tvTenChap = itemView.findViewById(R.id.tvTenChap);
        }
    }
}
