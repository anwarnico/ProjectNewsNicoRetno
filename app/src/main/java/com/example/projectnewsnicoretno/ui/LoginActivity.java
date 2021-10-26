package com.example.projectnewsnicoretno.ui;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.projectnewsnicoretno.R;
import com.example.projectnewsnicoretno.util.SessionManagerUtil;
import com.example.projectnewsnicoretno.viewmodel.NewsViewModel;

import java.util.Base64;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    EditText etUserName, etPass;
    ProgressBar progressBar;
    ConstraintLayout layoutLogin;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        newsViewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        btnLogin = findViewById(R.id.btnLogin);
        etUserName = findViewById(R.id.etUserName);
        etPass = findViewById(R.id.etPass);
        progressBar = findViewById(R.id.progressBar);
        layoutLogin = findViewById(R.id.layoutLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
                newsViewModel.getNewsFromKeyWord("health").observe(LoginActivity.this, news -> {
                    Log.d("TAG", news.toString());
                });
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(layoutLogin.getWindowToken(), 0);            }
        });
    }

    private void validateLogin(){
        username = etUserName.getText().toString();
        password = etPass.getText().toString();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, getString(R.string.emptyUserPassAlert), Toast.LENGTH_SHORT).show();
        }
        else if (username.equalsIgnoreCase("user") && password.equalsIgnoreCase("pass")) {
            login();
        }
        else {
            Toast.makeText(this, getString(R.string.wrongUserPassAlert), Toast.LENGTH_SHORT).show();
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
                        startAndStoreSession();
                        startMainActivity();
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
                .storeUserToken(this, generateToken(username, password));
        SessionManagerUtil.getInstance()
                .startUserSession(this, 5);
    }

    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        progressBar.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(true);
        startActivity(intent);
    }
}