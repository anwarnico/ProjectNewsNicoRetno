package com.example.projectnewsnicoretno.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.projectnewsnicoretno.BaseActivity;
import com.example.projectnewsnicoretno.R;
import com.example.projectnewsnicoretno.viewmodel.NewsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BaseActivity {
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
        Toolbar toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStackImmediate();
        }
        else{
            super.onBackPressed();
            finishAffinity();
        }
    }

}