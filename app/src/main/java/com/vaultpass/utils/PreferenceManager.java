package com.vaultpass.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREF_NAME = "VaultPassPrefs";
    private static final String KEY_IS_SETUP_COMPLETE = "is_setup_complete";
    private static final String KEY_MASTER_PASSWORD_HASH = "master_password_hash";
    private static final String KEY_USE_BIOMETRICS = "use_biometrics";
    private static final String KEY_THEME_MODE = "theme_mode"; // 0: Light, 1: Dark, 2: System

    private final SharedPreferences sharedPreferences;

    public PreferenceManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isSetupComplete() {
        return sharedPreferences.getBoolean(KEY_IS_SETUP_COMPLETE, false);
    }

    public void setSetupComplete(boolean complete) {
        sharedPreferences.edit().putBoolean(KEY_IS_SETUP_COMPLETE, complete).apply();
    }

    public String getMasterPasswordHash() {
        return sharedPreferences.getString(KEY_MASTER_PASSWORD_HASH, null);
    }

    public void setMasterPasswordHash(String hash) {
        sharedPreferences.edit().putString(KEY_MASTER_PASSWORD_HASH, hash).apply();
    }

    public boolean isUseBiometrics() {
        return sharedPreferences.getBoolean(KEY_USE_BIOMETRICS, false);
    }

    public void setUseBiometrics(boolean use) {
        sharedPreferences.edit().putBoolean(KEY_USE_BIOMETRICS, use).apply();
    }
}