package com.example.myapplication.Interface;


import com.example.myapplication.Module.TruyenTranh;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface TruyenTranhAPI {

    //https://demo0400039.mockable.io/getMangaAndroid45


    @GET("getMangaAndroid45")
    Call<List<TruyenTranh>> getListTruyenTranh();

}
