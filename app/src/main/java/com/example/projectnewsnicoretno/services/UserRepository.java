package com.example.projectnewsnicoretno.services;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

//import com.example.projectnewsnicoretno.model.UserData;
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

    private MutableLiveData<User> user = new MutableLiveData<>();

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

        //Room
        AppDatabase database = AppDatabase.getDatabase(application);
        userDao = database.userDao();
    }

    public void getUserFromNetwork(String username, String password) {
        networkExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    User userFromNetwork = API.getUserDetail(username, password).execute().body();
                    mainThread.execute(new Runnable() {
                        @Override
                        public void run() {
                            user.setValue(userFromNetwork);
                            if (userFromNetwork != null) {
                                UserProfile userProfile = new UserProfile();
                                userProfile.email = userFromNetwork.getData().getEmail();
                                userProfile.username = userFromNetwork.getData().getUsername();
                                userProfile.fullName = userFromNetwork.getData().getFullName();
                                userProfile.avatar = userFromNetwork.getData().getAvatar();
                                userProfile.lastLogin = userFromNetwork.getData().getLastLogin();
                                userProfile.lastActivity = userFromNetwork.getData().getLastActivity();
                                userProfile.dateCreated = userFromNetwork.getData().getDateCreated();
                                userProfile.companyId = userFromNetwork.getData().getCompanyId();
                                insert(userProfile);

                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LiveData<UserProfile> getUserByEmail(String email){
        return userDao.getUserByEmail(email);
    }

    public MutableLiveData<User> getUserDetail(String username, String password){
        if (user.getValue() == null) {
            getUserFromNetwork(username, password);
        }
        return user;
    }

    public void insert(UserProfile userProfile){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(userProfile);
            }
        });
    }

    public void update(UserProfile userProfile){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.update(userProfile);
            }
        });
    }

    public void delete(UserProfile userProfile){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                userDao.delete(userProfile);
            }
        });
    }

}

