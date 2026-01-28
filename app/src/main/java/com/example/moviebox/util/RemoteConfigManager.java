package com.example.moviebox.util;

import com.example.moviebox.R;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

public class RemoteConfigManager {

    private static final String SPLASH_LOGO_URL_KEY = "splash_logo_url";
    private static final String SPECIAL_BUTTON_VISIBLE_KEY = "special_button_visible";

    private final FirebaseRemoteConfig remoteConfig;

    public RemoteConfigManager() {
        remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600) // Fetch new values every hour
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);

        // Set default values
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);
    }

    public void fetchAndActivate() {
        remoteConfig.fetchAndActivate();
    }

    public String getSplashLogoUrl() {
        return remoteConfig.getString(SPLASH_LOGO_URL_KEY);
    }

    public boolean isSpecialButtonVisible() {
        return remoteConfig.getBoolean(SPECIAL_BUTTON_VISIBLE_KEY);
    }
}