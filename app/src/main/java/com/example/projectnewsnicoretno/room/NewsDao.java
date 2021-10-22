package com.example.projectnewsnicoretno.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectnewsnicoretno.model.ArticlesItem;
import com.example.projectnewsnicoretno.room.tables.Articles;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM articles")
    LiveData<List<Articles>> getAll();

    @Query("SELECT * FROM articles WHERE type IN (:type)")
    LiveData<List<Articles>> getArticlesByType(String type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Articles articles);

    @Update
    void update(Articles articles);

    @Delete
    void delete(Articles articles);
}

