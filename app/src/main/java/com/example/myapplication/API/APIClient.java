package com.example.myapplication.API;

import com.example.myapplication.Interface.TruyenTranhAPI;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    static String BASE_URL = "https://demo0400039.mockable.io/";
    static TruyenTranhAPI truyenTranhAPI;
    static Retrofit retrofit = null;
    public static TruyenTranhAPI getInstance(){
        if(truyenTranhAPI==null){
            OkHttpClient okHttpClient = new OkHttpClient();
            Retrofit restAdapter = newRetrofitInstance(BASE_URL,okHttpClient);
            truyenTranhAPI = restAdapter.create(TruyenTranhAPI.class);
        }
        return truyenTranhAPI;
    }

    private static Retrofit newRetrofitInstance(String baseUrl, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
