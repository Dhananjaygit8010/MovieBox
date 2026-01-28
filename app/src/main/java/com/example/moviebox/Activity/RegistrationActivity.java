package com.example.moviebox.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.moviebox.R;

public class RegistrationActivity extends AppCompatActivity
{
    EditText etRegName, etRegMbNo, etRegEmail, etRegUsername, etRegPassword, etRegConfirmPassword;
    Button btnReg;
    TextView tvLoginRegister;
    CardView cvReg;
    public boolean doubletap = false;
    SharedPreferences sp;
    SharedPreferences.Editor edit;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        edit = sp.edit();

        etRegName = findViewById(R.id.etRegName);
        etRegMbNo = findViewById(R.id.etRegMbNo);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegUsername = findViewById(R.id.etRegUsername);
        etRegPassword = findViewById(R.id.etRegPassword);
        etRegConfirmPassword = findViewById(R.id.etRegConfirmPassword);

        btnReg = findViewById(R.id.btnReg);
        tvLoginRegister = findViewById(R.id.tvLoginRegister);
        cvReg = findViewById(R.id.cvReg);

        btnReg.setOnClickListener(v ->
        {
            String name = etRegName.getText().toString().trim();
            String mobile = etRegMbNo.getText().toString().trim();
            String email = etRegEmail.getText().toString().trim();
            String username = etRegUsername.getText().toString().trim();
            String password = etRegPassword.getText().toString().trim();
            String confirmPassword = etRegConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(name))
            {
                etRegName.setError("Name required");
            }
            else if (TextUtils.isEmpty(mobile))
            {
                etRegMbNo.setError("Mobile number required");
            }
            else if (mobile.length() != 10)
            {
                etRegMbNo.setError("Enter 10 digit mobile number");
            }
            else if (TextUtils.isEmpty(email))
            {
                etRegEmail.setError("Email required");
            }
            else if (!email.contains("@") || !email.endsWith(".com"))
            {
                etRegEmail.setError("Enter valid email");
            }
            else if (TextUtils.isEmpty(username))
            {
                etRegUsername.setError("Username required");
            }
            else if (!username.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
            {
                etRegUsername.setError(
                        "Min 8 chars, 1 Upper, 1 Lower,\n1 Number, 1 Special"
                );
            }
            else if (TextUtils.isEmpty(password))
            {
                etRegPassword.setError("Password required");
            }
            else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"))
            {
                etRegPassword.setError(
                        "Min 8 chars, 1 Upper, 1 Lower,\n1 Number, 1 Special"
                );
            }
            else if (!password.equals(confirmPassword))
            {
                etRegConfirmPassword.setError("Passwords do not match");
            }
            else
            {
                edit.putString("username", username);
                edit.putString("password", password);
                edit.apply();
                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });

        tvLoginRegister.setOnClickListener(v ->
        {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        });
    }

    @SuppressLint("GestureBackNavigation")
    @Override
    public void onBackPressed()
    {
        if (doubletap)
        {
            finishAffinity();
            return;
        }

        this.doubletap = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubletap = false, 2000);
    }
}