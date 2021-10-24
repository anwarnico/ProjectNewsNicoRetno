package com.example.projectnewsnicoretno;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnewsnicoretno.adapter.ArticlesAdapter;
import com.example.projectnewsnicoretno.viewmodel.NewsViewModel;

import java.util.ArrayList;

public class TopHeadlineFragment extends Fragment {
    NewsViewModel newsViewModel;
    private RecyclerView recyclerView;
    private ArticlesAdapter articlesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.article_fragment_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerview);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        newsViewModel.getNewsFromKeyWord("top_headline").observe(getViewLifecycleOwner(), news -> {
            articlesAdapter = new ArticlesAdapter(news);
            recyclerView.setAdapter(articlesAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        });
    }
}