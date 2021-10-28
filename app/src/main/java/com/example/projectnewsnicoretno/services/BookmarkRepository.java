package com.example.projectnewsnicoretno.services;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.projectnewsnicoretno.room.AppDatabase;
import com.example.projectnewsnicoretno.room.BookmarkDao;
import com.example.projectnewsnicoretno.room.tables.Bookmark;

import java.util.List;

public class BookmarkRepository {
    private BookmarkDao bookmarkDao;
    private NewsEndPointInterface API;

    public BookmarkRepository(Application application){
        //Room
        AppDatabase database = AppDatabase.getDatabase(application);
        bookmarkDao = database.bookmarkDao();
    }

    public LiveData<List<Bookmark>> getAllBookmark(String userEmail){
        return bookmarkDao.getAllBookmark(userEmail);
    }

    public LiveData<Bookmark> getBookmarkByTitle(String title){
        return bookmarkDao.getBookmarkByTitle(title);
    }

    public void insert(Bookmark bookmark){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookmarkDao.insert(bookmark);
            }
        });
    }

    public void update(Bookmark bookmark){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookmarkDao.update(bookmark);
            }
        });
    }

    public void delete(Bookmark bookmark){
        AppDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                bookmarkDao.delete(bookmark);
            }
        });
    }
}
