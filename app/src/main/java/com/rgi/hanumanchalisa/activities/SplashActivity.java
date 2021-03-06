package com.rgi.hanumanchalisa.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavOptions;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.rgi.hanumanchalisa.R;
import com.rgi.hanumanchalisa.utils.Constant;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "run: inside splash ");
                startActivity(new Intent(SplashActivity.this, MainActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        }, Constant.SPLASH_TIME_OUT);
    }
}