package com.example.projectnewsnicoretno.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectnewsnicoretno.R;
import com.example.projectnewsnicoretno.adapter.ArticlesAdapter;
import com.example.projectnewsnicoretno.adapter.BookmarkAdapter;
import com.example.projectnewsnicoretno.viewmodel.BookmarkViewModel;
import com.example.projectnewsnicoretno.viewmodel.NewsViewModel;

public class BookmarksFragment extends Fragment {
    BookmarkViewModel bookmarkViewModel;
    private RecyclerView recyclerView;
    private BookmarkAdapter bookmarkAdapter;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCE_NAME = "com.example.projectnewsnicoretno.sharedpref";
    String email;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        bookmarkViewModel = new ViewModelProvider(requireActivity()).get(BookmarkViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerviewBookmark);
        email = sharedPreferences.getString("email", "defaultEmail");

        bookmarkViewModel.getAllBookmark(email).observe(getViewLifecycleOwner(), bookmark -> {
            bookmarkAdapter = new BookmarkAdapter(bookmark);
            recyclerView.setAdapter(bookmarkAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        });
    }
}