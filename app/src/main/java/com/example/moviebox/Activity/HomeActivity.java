package com.example.moviebox.Activity;
//daily commit 
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import com.example.moviebox.Fragment.AllcourseFragment;
import com.example.moviebox.Fragment.DownloadFragment;
import com.example.moviebox.Fragment.HomeFragment;
import com.example.moviebox.Fragment.MycourseFragment;
import com.example.moviebox.MyApplication;
import com.example.moviebox.R;
import com.example.moviebox.model.ExampleResponse;
import com.example.moviebox.network.ApiClient;
import com.example.moviebox.network.ApiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    public boolean doubletap = false;
    SharedPreferences sp;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sp = PreferenceManager.getDefaultSharedPreferences(this);

        boolean isLogin = sp.getBoolean("islogin", false);
        if (!isLogin) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
            return;
        }

        boolean isFirstTime = sp.getBoolean("isFirstTime", true);
        if (isFirstTime) {
            welcomeDialog();
        } else {
            Toast.makeText(this, "Welcome Back", Toast.LENGTH_SHORT).show();
        }

        bottomNavigationView = findViewById(R.id.homeBottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(this);

        // Set the visibility of the download button based on Remote Config
        boolean isSpecialButtonVisible = MyApplication.getRemoteConfigManager().isSpecialButtonVisible();
        bottomNavigationView.getMenu().findItem(R.id.HomeNavDownload).setVisible(isSpecialButtonVisible);

        getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, new HomeFragment()).commit();

        fetchExampleData();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubletap) {
                    finishAffinity();
                } else {
                    doubletap = true;
                    Toast.makeText(HomeActivity.this, "Press again to exit", Toast.LENGTH_SHORT).show();
                    new Handler(Looper.getMainLooper()).postDelayed(() -> doubletap = false, 2000);
                }
            }
        });
    }

    private void fetchExampleData() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<ExampleResponse> call = apiService.getExampleData();
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<ExampleResponse> call, @NonNull Response<ExampleResponse> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(HomeActivity.this, "API call successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle error response
                    Toast.makeText(HomeActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ExampleResponse> call, @NonNull Throwable t) {
                // Handle failure
                Log.e("HomeActivity", "API call failed", t);
                Toast.makeText(HomeActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void welcomeDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Movie Box App")
                .setMessage("Welcome to Movie Box App")
                .setIcon(R.drawable.img)
                .setPositiveButton("Thank You", (dialog, which) -> dialog.cancel())
                .create().show();

        sp.edit().putBoolean("isFirstTime", false).apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.homeMenuMyProfile) {
            startActivity(new Intent(HomeActivity.this, MyPorfileActivity.class));
            Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.homeMenuLocation) {
            Intent i = new Intent(HomeActivity.this, MyLocationActivity.class);
            startActivity(i);
            Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.homeMenuSetting) {
            startActivity(new Intent(HomeActivity.this, SettingActivity.class));
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.homeMenuAboutUs) {
            startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
            Toast.makeText(this, "About Us", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.homeMenuContactUs) {
            startActivity(new Intent(HomeActivity.this, ContactUsActivity.class));
            Toast.makeText(this, "Contact Us", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.homeMenuLogout) {
            logout();
        }
        return true;
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setIcon(R.drawable.icon_logout)
                .setPositiveButton("Logout", (dialog, which) -> {
                    sp.edit().putBoolean("islogin", false).apply();

                    Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.cancel())
                .create().show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        int itemId = menuItem.getItemId();
        if (itemId == R.id.homeNavHome) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.HomeNavAllCourse) {
            selectedFragment = new AllcourseFragment();
        } else if (itemId == R.id.HomeNavMyCourse) {
            selectedFragment = new MycourseFragment();
        } else if (itemId == R.id.HomeNavDownload) {
            selectedFragment = new DownloadFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, selectedFragment).commit();
        }
        return true;
    }
}
