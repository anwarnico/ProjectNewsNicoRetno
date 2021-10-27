package com.example.projectnewsnicoretno.ui;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.transition.AutoTransition;
import android.transition.Fade;
import android.transition.Slide;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnewsnicoretno.R;
import com.example.projectnewsnicoretno.adapter.ArticlesAdapter;
import com.example.projectnewsnicoretno.room.tables.Articles;
import com.example.projectnewsnicoretno.viewmodel.NewsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    RecyclerView recyclerViewSearch;
    BottomNavigationView bottomNavigationView;
    NewsViewModel newsViewModel;
    Toolbar toolBar;
    List<Articles> articlesList, searchList;
    private ArticlesAdapter articlesAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        recyclerViewSearch = view.findViewById(R.id.recyclerViewSearch);
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        toolBar = getActivity().findViewById(R.id.toolBar);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        newsViewModel.getAllNews().observe(getViewLifecycleOwner(), news -> {
            articlesList = news;
            searchList = new ArrayList<>(articlesList);
            articlesAdapter = new ArticlesAdapter(news);
            recyclerViewSearch.setAdapter(articlesAdapter);
            recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        SearchManager sm = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView sv = (SearchView) menu.findItem(R.id.search).getActionView();
        sv.setSearchableInfo(sm.getSearchableInfo(getActivity().getComponentName()));
        sv.setIconifiedByDefault(false);
        sv.setMaxWidth(Integer.MAX_VALUE);
        sv.setQueryHint(getString(R.string.search));
        sv.requestFocus();
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    resetData();
                    return true;
                }
                else {
                    filter(s);
                }
                return false;
            }
        });
    }

    private void filter(String query) {
        query = query.toLowerCase();
        articlesList.clear();
        if (query.length() == 0) {
            articlesList.addAll(searchList);
        } else {
            for (Articles articles : searchList) {
                if (articles.title.toLowerCase().contains(query.toLowerCase())) {
                    articlesList.add(articles);
                }
            }
        }
        articlesAdapter.notifyDataSetChanged();
    }

    private void resetData(){
        articlesList.clear();
        articlesList.addAll(searchList);
        articlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bottomNavigationView.setVisibility(View.VISIBLE);
        toolBar.setVisibility(View.GONE);
    }
}