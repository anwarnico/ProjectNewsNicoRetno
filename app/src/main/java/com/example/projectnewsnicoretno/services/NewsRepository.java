package com.example.projectnewsnicoretno.services;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsnicoretno.BuildConfig;
import com.example.projectnewsnicoretno.model.News;
import com.example.projectnewsnicoretno.room.AppDatabase;
import com.example.projectnewsnicoretno.room.NewsDao;
import com.example.projectnewsnicoretno.room.tables.Articles;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewsRepository {
    private NewsDao newsDao;
    private LiveData<List<Articles>> allArticle;
    private LiveData<List<Articles>> articleFromKeyword;
    private NewsEndPointInterface API;
    private MutableLiveData<News> newsTopHeadline = new MutableLiveData<>();
    private MutableLiveData<News> newsBaseOnKeyword = new MutableLiveData<>();

    private final ExecutorService networkExecutor =
            Executors.newFixedThreadPool(4);
    private final Executor mainThread = new Executor() {
        private Handler handler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };

    public NewsRepository(Application application){
        //Retrofit
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        API = retrofitInstance.getAPI();

        //Room
        AppDatabase database = AppDatabase.getDatabase(application);
        newsDao = database.newsDao();
    }

    public void getTopHeadlineNewsFromNetwork(String country){
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    News newsFromNetwork = API.getListTopHeadlineNews(country, BuildConfig.NEWS_API_KEY).execute().body();
                    mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            newsTopHeadline.setValue(newsFromNetwork);
                            if (newsFromNetwork != null) {
                                for (int i = 0; i < 19; i++) {
                                    Articles articles = new Articles();
                                    articles.publishedAt = newsFromNetwork.getArticles().get(i).publishedAt;
                                    articles.author = newsFromNetwork.getArticles().get(i).author;
                                    articles.urlToImage = newsFromNetwork.getArticles().get(i).urlToImage;
                                    articles.description = newsFromNetwork.getArticles().get(i).description;
                                    articles.title = newsFromNetwork.getArticles().get(i).title;
                                    articles.url = newsFromNetwork.getArticles().get(i).url;
                                    articles.content = newsFromNetwork.getArticles().get(i).content;
                                    articles.type = "top_headline";
                                    insert(articles);
                                }
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getListNewsBaseOnKeywordFromNetwork(String keyWord) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    News newsKeyWordFromNetwork = API.getListNewsBaseOnKeyword(keyWord, "en", BuildConfig.NEWS_API_KEY).execute().body();
                    mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            newsBaseOnKeyword.setValue(newsKeyWordFromNetwork);
                            if (newsKeyWordFromNetwork != null) {
                                for (int i = 0; i < newsKeyWordFromNetwork.getArticles().size(); i++) {
                                    Articles articles = new Articles();
                                    articles.publishedAt = newsKeyWordFromNetwork.getArticles().get(i).publishedAt;
                                    articles.author = newsKeyWordFromNetwork.getArticles().get(i).author;
                                    articles.urlToImage = newsKeyWordFromNetwork.getArticles().get(i).urlToImage;
                                    articles.description = newsKeyWordFromNetwork.getArticles().get(i).description;
                                    articles.title = newsKeyWordFromNetwork.getArticles().get(i).title;
                                    articles.url = newsKeyWordFromNetwork.getArticles().get(i).url;
                                    articles.content = newsKeyWordFromNetwork.getArticles().get(i).content;
                                    articles.type = keyWord;
                                    insert(articles);
                                }
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LiveData<List<Articles>> getNewsTopHeadline(String country, String keyWord){
        if (newsTopHeadline.getValue() == null || newsTopHeadline.getValue().getTotalResults() == 0) {
            getTopHeadlineNewsFromNetwork(country);
        }
        return newsDao.getArticlesByType(keyWord);
    }

    public LiveData<List<Articles>> getAllNews(){
        return newsDao.getAll();
    }

    public LiveData<List<Articles>> getNewsFromKeyWord(String keyWord){
        if (newsBaseOnKeyword.getValue() == null || newsBaseOnKeyword.getValue().getTotalResults() == 0) {
            getListNewsBaseOnKeywordFromNetwork(keyWord);
        }
        return newsDao.getArticlesByType(keyWord);
    }

    void insert(Articles articles){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                newsDao.insert(articles);
            }
        });
    }

    void update(Articles articles){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                newsDao.update(articles);
            }
        });
    }

    void delete(Articles articles){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                newsDao.delete(articles);
            }
        });
    }
}
