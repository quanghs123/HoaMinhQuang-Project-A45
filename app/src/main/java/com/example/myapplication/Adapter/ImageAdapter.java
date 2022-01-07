package com.example.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.myapplication.Module.Image;
import com.example.myapplication.R;

import java.util.List;


public class ImageAdapter extends PagerAdapter {

    Context mContext;
    List<Image> mImageList;

    public ImageAdapter(Context mContext, List<Image> mImageList) {
        this.mContext = mContext;
        this.mImageList = mImageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_img_manga,container,false);
        ImageView imgManga = view.findViewById(R.id.imgManga);

        Image image = mImageList.get(position);
        if (image!=null){
            Glide.with(mContext).load(image.getLinkAnh()).into(imgManga);
        }
        //Add to ViewGroup
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(mImageList!=null){
            return mImageList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view ==object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
