package com.example.projectnewsnicoretno.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectnewsnicoretno.room.tables.Bookmark;
import com.example.projectnewsnicoretno.room.tables.UserProfile;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Query("SELECT * FROM bookmark WHERE userEmail = :userEmail")
    LiveData<Bookmark> getAllBookmark(String userEmail);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Bookmark bookmark);

    @Update
    void update(Bookmark bookmark);

    @Delete
    void delete(Bookmark bookmark);
}

