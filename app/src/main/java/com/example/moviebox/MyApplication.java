package com.example.moviebox;

import android.app.Application;
import android.content.Context;

import com.example.moviebox.util.RemoteConfigManager;

public class MyApplication extends Application {

    private static Context context;
    private static RemoteConfigManager remoteConfigManager;

    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();

        // Initialize Remote Config
        remoteConfigManager = new RemoteConfigManager();
        remoteConfigManager.fetchAndActivate();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

    public static RemoteConfigManager getRemoteConfigManager() {
        return remoteConfigManager;
    }
}