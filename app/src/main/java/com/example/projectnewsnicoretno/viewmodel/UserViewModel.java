package com.example.projectnewsnicoretno.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
;
//import com.example.projectnewsnicoretno.model.UserData;
import com.example.projectnewsnicoretno.model.User;
import com.example.projectnewsnicoretno.model.UserData;
import com.example.projectnewsnicoretno.room.tables.Articles;
import com.example.projectnewsnicoretno.room.tables.UserProfile;
import com.example.projectnewsnicoretno.services.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private MutableLiveData<User> userProfile = new MutableLiveData<>();
    private LiveData<UserProfile> userByEmail = new MutableLiveData<>();


    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }
    public MutableLiveData<User> getUserDetail(String username, String password){
        userProfile = userRepository.getUserDetail(username, password);
        return userProfile;
    }

    public LiveData<UserProfile> getUserByEmail(String email){
        userByEmail = userRepository.getUserByEmail(email);
        return userByEmail;
    }
}
