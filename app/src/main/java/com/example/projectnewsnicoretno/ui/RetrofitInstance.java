package com.example.projectnewsnicoretno.ui;

import com.example.projectnewsnicoretno.ui.LoginEndPointInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private LoginEndPointInterface API;

    public RetrofitInstance(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(LoginEndPointInterface.URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        retrofit.create(LoginEndPointInterface.class);
        API = retrofit.create(LoginEndPointInterface.class);
    }

    public LoginEndPointInterface getAPI(){
        return API;
    }

}
