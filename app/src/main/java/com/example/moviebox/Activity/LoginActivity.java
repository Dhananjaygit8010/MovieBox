package com.example.moviebox.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import com.example.moviebox.R;

public class LoginActivity extends AppCompatActivity {
    ImageView ivSplashLogo;
    TextView tvSplashTitle, tvLoginRegister, tvLoginTitle;
    EditText etLoginUsername, etLoginPassword;
    Button btnLogin, sample;
    CardView cardView;

    SharedPreferences sp;
    public boolean doubletap = false;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isLogin = sp.getBoolean("islogin", false);
        if (isLogin) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            return;
        }

        ivSplashLogo = findViewById(R.id.ivSplashLogo);
        tvSplashTitle = findViewById(R.id.tvSplashTitle);
        tvLoginRegister = findViewById(R.id.tvLoginRegister);
        etLoginUsername = findViewById(R.id.etLoginUsername);
        etLoginPassword = findViewById(R.id.etLoginPassword);
        tvLoginTitle = findViewById(R.id.tvLoginTitle);
        btnLogin = findViewById(R.id.btnLogin);
        cardView = findViewById(R.id.cvLogin);

        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        Animation slide = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        Animation fade = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);

        ivSplashLogo.startAnimation(scale);
        cardView.startAnimation(slide);
        tvLoginTitle.startAnimation(fade);

        btnLogin.setOnClickListener(v -> {
            String username = etLoginUsername.getText().toString().trim();
            String password = etLoginPassword.getText().toString().trim();

            String savedUsername = sp.getString("username", "Admin@123");
            String savedPassword = sp.getString("password", "Admin@123");

            if (TextUtils.isEmpty(username)) {
                etLoginUsername.setError("Please Enter Username");
                etLoginUsername.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                etLoginPassword.setError("Enter Password");
                etLoginPassword.requestFocus();
            } else if (username.equals(savedUsername) && password.equals(savedPassword)) {
                sp.edit().putBoolean("islogin", true).putString("token", "dummy-token-for-admin").apply();

                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

                Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        });

        tvLoginRegister.setOnClickListener(v -> {
            Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(i);
            finish();
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubletap) {
                    finishAffinity();
                } else {
                    doubletap = true;
                    Toast.makeText(LoginActivity.this, "Press again to exit", Toast.LENGTH_SHORT).show();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> doubletap = false, 2000);
                }
            }
        });
    }
}