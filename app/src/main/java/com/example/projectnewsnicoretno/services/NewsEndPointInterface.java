package com.example.projectnewsnicoretno.services;

import com.example.projectnewsnicoretno.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsEndPointInterface {

    String BASE_URL = "https://newsapi.org";

    @GET("/v2/top-headlines")
    Call<News> getListTopHeadlineNews(@Query("country") String country, @Query("apiKey") String NEWS_API_KEY);

    @GET("/v2/everything")
    Call<News> getListNewsBaseOnKeyword(@Query("q") String keyWord, @Query("language") String language, @Query("apiKey") String NEWS_API_KEY);

}
