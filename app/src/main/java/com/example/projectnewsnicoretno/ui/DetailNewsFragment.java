package com.example.projectnewsnicoretno.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.projectnewsnicoretno.R;
import com.example.projectnewsnicoretno.room.tables.Articles;
import com.example.projectnewsnicoretno.room.tables.Bookmark;
import com.example.projectnewsnicoretno.viewmodel.BookmarkViewModel;
import com.example.projectnewsnicoretno.viewmodel.NewsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DetailNewsFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCE_NAME = "com.example.projectnewsnicoretno.sharedpref";
    NewsViewModel newsViewModel;
    BookmarkViewModel bookmarkViewModel;
    BottomNavigationView bottomNavigationView;
    Toolbar toolBar;
    TextView tvPublishedAt, tvContent;
    ImageButton btnBookmark;
    ImageView ivNews;
    String email;
    Bookmark bookmark = new Bookmark();
    Articles articlesData = new Articles();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        bookmarkViewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_detail, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = sharedPreferences.getString("title", "NoArticle");
        email = sharedPreferences.getString("email", "defaultEmail");
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        toolBar = getActivity().findViewById(R.id.toolBar);
        bottomNavigationView.setVisibility(View.GONE);
        toolBar.setVisibility(View.GONE);


        tvContent = view.findViewById(R.id.tvContent);
        tvPublishedAt = view.findViewById(R.id.tvPublishedAt);
        btnBookmark = view.findViewById(R.id.btnBookmark);
        ivNews = view.findViewById(R.id.ivNews);

        newsViewModel.getArticlesByTitle(title).observe(getViewLifecycleOwner(), articles -> {
            LocalDateTime localDateTime = LocalDateTime.parse(articles.publishedAt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
            String formattedDate = localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            if (articles.content != null) {
                tvContent.setText(articles.content);
            }
            else {
                tvContent.setText(articles.description);
            }
            tvPublishedAt.setText(formattedDate);
            if (articles.urlToImage != null) {
                Glide.with(this)
                        .load(articles.urlToImage)
                        .fitCenter()
                        .into(ivNews);
            }
            else {
                ivNews.setImageResource(R.drawable.news);
            }
            articlesData = articles;
        });

        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertBookmark(articlesData);
            }
        });
    }

    public void insertBookmark(Articles articles) {
        bookmark.author = articles.author;
        bookmark.content = articles.content;
        bookmark.description = articles.description;
        bookmark.publishedAt = articles.publishedAt;
        bookmark.title = articles.title;
        bookmark.type = articles.type;
        bookmark.url = articles.url;
        bookmark.urlToImage = articles.urlToImage;
        bookmark.userEmail = email;
        bookmarkViewModel.insert(bookmark);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bottomNavigationView.setVisibility(View.VISIBLE);
        toolBar.setVisibility(View.VISIBLE);
    }
}