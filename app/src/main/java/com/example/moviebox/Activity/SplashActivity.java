package com.example.moviebox.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.moviebox.MyApplication;
import com.example.moviebox.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashLogo = findViewById(R.id.ivSplashLogo);

        String splashLogoUrl = MyApplication.getRemoteConfigManager().getSplashLogoUrl();
        if (!TextUtils.isEmpty(splashLogoUrl)) {
            Glide.with(this).load(splashLogoUrl).into(splashLogo);
        }

        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }, 3000);
    }
}