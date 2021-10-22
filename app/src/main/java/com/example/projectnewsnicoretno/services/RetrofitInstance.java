package com.example.projectnewsnicoretno.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private NewsEndPointInterface API;

    public RetrofitInstance(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NewsEndPointInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofit.create(NewsEndPointInterface.class);
        API = retrofit.create(NewsEndPointInterface.class);
    }

    public NewsEndPointInterface getAPI(){
        return API;
    }

}
