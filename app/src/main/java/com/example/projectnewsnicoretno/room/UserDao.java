package com.example.projectnewsnicoretno.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectnewsnicoretno.room.tables.UserProfile;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM UserProfile")
    LiveData<List<UserProfile>> getAll();

    @Query("SELECT * FROM UserProfile WHERE username IN (:username)")
    LiveData<UserProfile> getUserByUsername(String username);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(UserProfile userProfile);

    @Update
    void update(UserProfile userProfile);

    @Delete
    void delete(UserProfile userProfile);
}

