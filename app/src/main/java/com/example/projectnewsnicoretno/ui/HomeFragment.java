package com.example.projectnewsnicoretno.ui;

import android.app.Activity;
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
    MainActivity mainActivity;
    Toolbar toolBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.searchView);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, new SearchFragment(), "SearchFragmentTag").addToBackStack("SearchFragmentTag").commit();
                bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                toolBar = getActivity().findViewById(R.id.toolBar);
                bottomNavigationView.setVisibility(View.GONE);
                toolBar.setVisibility(View.VISIBLE);
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
}