package com.example.projectnewsnicoretno.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.projectnewsnicoretno.model.News;
import com.example.projectnewsnicoretno.room.tables.Articles;
import com.example.projectnewsnicoretno.room.tables.UserProfile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Articles.class, UserProfile.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
    public abstract UserDao userDao();

    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "news_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
