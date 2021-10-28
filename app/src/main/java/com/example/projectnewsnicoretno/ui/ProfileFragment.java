package com.example.projectnewsnicoretno.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectnewsnicoretno.R;
import com.example.projectnewsnicoretno.util.SessionManagerUtil;
import com.example.projectnewsnicoretno.viewmodel.UserViewModel;

public class ProfileFragment extends Fragment {

    ImageView ivAvatar;
    TextView tvNama, tvEmail;
    UserViewModel userViewModel;
    Button btnLogout;
    private SharedPreferences sharedPreferences;
    public static final String SHARED_PREFERENCE_NAME = "com.example.projectnewsnicoretno.ProfileFragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String email = sharedPreferences.getString("email", "defaultEmail");
        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvNama = view.findViewById(R.id.tvNama);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnLogout = view.findViewById(R.id.btnLogout);

        userViewModel.getUserByEmail(email).observe(getViewLifecycleOwner(), user -> {
            tvNama.setText(user.fullName);
            tvEmail.setText(user.email);
            ivAvatar.setImageResource(R.drawable.avatar);
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SessionManagerUtil.getInstance().endUserSession(getContext());
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }
}