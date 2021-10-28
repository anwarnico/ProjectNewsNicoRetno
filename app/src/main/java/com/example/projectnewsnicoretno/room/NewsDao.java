package com.example.projectnewsnicoretno.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectnewsnicoretno.room.tables.Articles;
import com.example.projectnewsnicoretno.room.tables.Bookmark;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    LiveData<List<Articles>> getAll();

    @Query("SELECT * FROM articles WHERE type IN (:type) ORDER BY publishedAt DESC")
    LiveData<List<Articles>> getArticlesByType(String type);

    @Query("SELECT * FROM articles WHERE title = :title")
    LiveData<Articles> getArticlesByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Articles articles);

    @Update
    void update(Articles articles);

    @Delete
    void delete(Articles articles);
}

