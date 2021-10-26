package com.example.projectnewsnicoretno.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.projectnewsnicoretno.R;
import com.example.projectnewsnicoretno.viewmodel.NewsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    final FragmentManager fm = getSupportFragmentManager();
    Fragment home = new HomeFragment();
    Fragment bookmarks = new BookmarksFragment();
    Fragment profile = new ProfileFragment();
    NewsViewModel newsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        fm.beginTransaction().replace(R.id.container, home).show(home).commit();
        fm.beginTransaction().add(R.id.container, bookmarks).hide(bookmarks).commit();
        fm.beginTransaction().add(R.id.container, profile).hide(profile).commit();
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        newsViewModel.active = home;
    }

    public BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.home:
                            fm.beginTransaction().hide(newsViewModel.active).show(home).commit();
                            newsViewModel.active = home;
                            break;
                        case R.id.bookmarks:
                            fm.beginTransaction().hide(newsViewModel.active).show(bookmarks).commit();
                            newsViewModel.active = bookmarks;
                            break;
                        case R.id.profile:
                            fm.beginTransaction().hide(newsViewModel.active).show(profile).commit();
                            newsViewModel.active = profile;
                            break;
                    }

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveTaskToBack(true);
        finishAffinity();
    }
}