package com.example.projectnewsnicoretno.services;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.projectnewsnicoretno.model.User;
import com.example.projectnewsnicoretno.model.UserData;
import com.example.projectnewsnicoretno.room.AppDatabase;
import com.example.projectnewsnicoretno.room.UserDao;
import com.example.projectnewsnicoretno.room.tables.Articles;
import com.example.projectnewsnicoretno.room.tables.UserProfile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRepository {
    private UserDao userDao;
    private LoginEndPointInterface API;

    private MutableLiveData<UserData> userData = new MutableLiveData<>();

    private final ExecutorService networkExecutor =
            Executors.newFixedThreadPool(4);
    private final Executor mainThread = new Executor() {
        private Handler handler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable command) {
            handler.post(command);
        }
    };

    public UserRepository(Application application) {
        RetrofitInstance retrofitInstance = new RetrofitInstance();
        API = retrofitInstance.getLoginAPI();
    }

    public void getUserFromNetwork(String username, String password) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    UserData userFromNetwork = API.getUserDetail(username, password).execute().body();
                    mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            userData.setValue(userFromNetwork);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getUserDetailFromNetwork(String username, String password) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    UserData userDataFromNetwork = API.getUserDetail(username, password).execute().body();
                    mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            userData.setValue(userDataFromNetwork);
                            if (userDataFromNetwork != null) {
                                for (int i = 0; i < 19; i++) {
                                    UserProfile userProfile = new UserProfile();
                                    userProfile.username = userDataFromNetwork.getId();
                                    userProfile.username = userDataFromNetwork.getEmail();
                                    userProfile.username = userDataFromNetwork.getUsername();
                                    userProfile.username = userDataFromNetwork.getFullName();
                                    userProfile.username = userDataFromNetwork.getAvatar();
                                    userProfile.username = userDataFromNetwork.getLastLogin();
                                    userProfile.username = userDataFromNetwork.getLastActivity();
                                    userProfile.username = userDataFromNetwork.getDateCreated();
                                    userProfile.username = userDataFromNetwork.getCompanyId();
                                    insert(userProfile);
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

    public LiveData<UserProfile> getUserDetail(String username, String password){
        if (userData.getValue() == null) {
            getUserFromNetwork(username, password);
        }
        return userDao.getUserByUsername(username);
    }

    void insert(UserProfile userProfile){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(userProfile);
            }
        });
    }

    void update(UserProfile userProfile){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.update(userProfile);
            }
        });
    }

    void delete(UserProfile userProfile){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.delete(userProfile);
            }
        });
    }

}

