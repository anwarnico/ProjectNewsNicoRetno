package com.example.projectnewsnicoretno.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.projectnewsnicoretno.room.tables.Bookmark;
import com.example.projectnewsnicoretno.services.BookmarkRepository;

;import java.util.List;

public class BookmarkViewModel extends AndroidViewModel {

    private BookmarkRepository bookmarkRepository;


    public BookmarkViewModel(@NonNull Application application) {
        super(application);
        bookmarkRepository = new BookmarkRepository(application);
    }

    public LiveData<List<Bookmark>> getAllBookmark(String userEmail) {
        return bookmarkRepository.getAllBookmark(userEmail);
    }

    public void insert(Bookmark bookmark) {
        bookmarkRepository.insert(bookmark);
    }

    public void update(Bookmark bookmark) {
        bookmarkRepository.update(bookmark);
    }

    public void delete(Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
    }
}
