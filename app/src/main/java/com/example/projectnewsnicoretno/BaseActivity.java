package com.example.projectnewsnicoretno;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectnewsnicoretno.ui.LoginActivity;
import com.example.projectnewsnicoretno.util.SessionManagerUtil;

import java.util.Calendar;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        boolean isAllowed = SessionManagerUtil.getInstance()
                .isSessionActive(this, Calendar.getInstance().getTime());
        if (!isAllowed) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        super.onResume();
    }
}
