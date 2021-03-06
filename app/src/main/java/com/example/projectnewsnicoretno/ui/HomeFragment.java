package com.example.projectnewsnicoretno.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projectnewsnicoretno.R;
import com.example.projectnewsnicoretno.viewmodel.NewsViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class HomeFragment extends Fragment {
    BottomNavigationView bottomNavigationView;
    ConstraintLayout searchView;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCE_NAME = "com.example.projectnewsnicoretno.sharedpref";
    Toolbar toolBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        removePreferenceQuery();
        bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
        toolBar = getActivity().findViewById(R.id.toolBar);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, new SearchFragment(), "SearchFragmentTag").addToBackStack("SearchFragmentTag").commit();
            }
        });

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getContext())
                .add(R.string.top_headline, TopHeadlineFragment.class)
                .add(R.string.health, HealthFragment.class)
                .add(R.string.politics, PoliticsFragment.class)
                .add(R.string.art, ArtFragment.class)
                .add(R.string.food, FoodFragment.class)
                .create());

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.tab_layout);
        viewPagerTab.setViewPager(viewPager);
    }

    private void removePreferenceQuery() {
        String query = sharedPreferences.getString("query", "defaultQuery");
        if (query != "defaultQuery") {
            sharedPreferences.edit().remove("query").apply();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView.setVisibility(View.VISIBLE);
        toolBar.setVisibility(View.GONE);
    }
}