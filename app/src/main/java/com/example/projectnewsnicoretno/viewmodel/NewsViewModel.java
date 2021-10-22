package com.example.projectnewsnicoretno.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsnicoretno.room.tables.Articles;
import com.example.projectnewsnicoretno.services.NewsRepository;
import com.example.projectnewsnicoretno.model.News;

import java.util.List;


public class NewsViewModel extends AndroidViewModel {

    private NewsRepository newsRepository;
    private LiveData<List<Articles>> newsTopHeadline = new MutableLiveData<>();
    private LiveData<List<Articles>> newsBaseOnKeyword = new MutableLiveData<>();
    private LiveData<List<Articles>> newsHealths = new MutableLiveData<>();
    private LiveData<List<Articles>> newsPolitics = new MutableLiveData<>();
    private LiveData<List<Articles>> newsArts = new MutableLiveData<>();
    private LiveData<List<Articles>> newsFoods = new MutableLiveData<>();

    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);
        newsTopHeadline = newsRepository.getNewsTopHeadline("us");
        newsHealths = newsRepository.getNewsFromKeyWord("health");
        newsPolitics = newsRepository.getNewsFromKeyWord("politics");
        newsArts = newsRepository.getNewsFromKeyWord("arts");
        newsFoods = newsRepository.getNewsFromKeyWord("foods");
    }

    public LiveData<List<Articles>> getNewsTopHeadline(){
        return newsTopHeadline;
    }

    public LiveData<List<Articles>> getNewsFromKeyWord(String keyWord){
        newsBaseOnKeyword = newsRepository.getNewsFromKeyWord(keyWord);
        return newsBaseOnKeyword;
    }

    public LiveData<List<Articles>> getNewsHealths(){
        return newsHealths;
    }

    public LiveData<List<Articles>> getNewsPolitics(){
        return newsPolitics;
    }

    public LiveData<List<Articles>> getNewsArts(){
        return newsArts;
    }

    public LiveData<List<Articles>> getNewsFoods(){
        return newsFoods;
    }

}
