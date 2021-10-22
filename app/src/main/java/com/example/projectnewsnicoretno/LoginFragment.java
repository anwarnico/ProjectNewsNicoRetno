package com.example.projectnewsnicoretno;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.projectnewsnicoretno.util.SessionManagerUtil;
import com.example.projectnewsnicoretno.viewmodel.NewsViewModel;

import java.util.Base64;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginFragment extends Fragment {

    EditText etUserName, etPass;
    ProgressBar progressBar;
    Button btnLogin;
    NewsViewModel newsViewModel;
    private String username, password;
    private Executor backgroundThread = Executors.newSingleThreadExecutor();
    private Executor mainThread = new Executor() {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(Runnable command) {
            mainThreadHandler.post(command);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsViewModel = new ViewModelProvider(requireActivity()).get(NewsViewModel.class);
        btnLogin = view.findViewById(R.id.btnLogin);
        etUserName = view.findViewById(R.id.etUserName);
        etPass = view.findViewById(R.id.etPass);
        progressBar = view.findViewById(R.id.progressBar);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
                newsViewModel.getNewsTopHeadline().observe(getViewLifecycleOwner(), news -> {
                    Log.d("TAG", news.toString());
                });
                newsViewModel.getNewsFromKeyWord("dogs").observe(getViewLifecycleOwner(), news -> {
                    Log.d("TAG", news.toString());
                });
            }
        });

    }

    private void validateLogin(){

        username = etUserName.getText().toString();
        password = etPass.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), getString(R.string.emptyUserPassAlert), Toast.LENGTH_SHORT).show();
        }
        else if (username.equalsIgnoreCase("user") && password.equalsIgnoreCase("pass")) {
            login();
        }
        else {
            Toast.makeText(getActivity(), getString(R.string.wrongUserPassAlert), Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void login() {
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.INVISIBLE);
        btnLogin.setEnabled(false);
        backgroundThread.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                // connect server
                SystemClock.sleep(1500);
                mainThread.execute(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        btnLogin.setVisibility(View.VISIBLE);
                        btnLogin.setEnabled(true);
                        startAndStoreSession();
                        startFragment();
                    }
                });
            }
        });

    }

    private String generateToken(String username, String password){
        String feeds = username+":"+password;
        String token = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            token = Base64.getEncoder().encodeToString(feeds.getBytes());
        } else {
            token = feeds;
        }
        return token;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startAndStoreSession(){
        SessionManagerUtil.getInstance()
                .storeUserToken(requireActivity(), generateToken(username, password));
        SessionManagerUtil.getInstance()
                .startUserSession(requireActivity(), 5);
    }

    private void startFragment() {
//        getActivity().getSupportFragmentManager().
//                beginTransaction().replace(R.id.container, new SecondFragment()).commitNow();
    }

}
