package com.vaultpass.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.vaultpass.utils.PreferenceManager;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View logo = findViewById(R.id.iv_logo);
        View appName = findViewById(R.id.tv_app_name);
        View tagline = findViewById(R.id.tv_tagline);
        View glow = findViewById(R.id.glow_bg);

        // Logo Scale & Bounce
        logo.setScaleX(0f);
        logo.setScaleY(0f);
        logo.animate().scaleX(1f).scaleY(1f).setDuration(1200).setInterpolator(new OvershootInterpolator()).start();

        // Glow Pulse
        glow.setAlpha(0f);
        glow.animate().alpha(1f).setDuration(2000).start();

        // Text animations
        appName.setAlpha(0f);
        appName.setTranslationY(100f);
        appName.animate().alpha(1f).translationY(0f).setDuration(1000).setStartDelay(400).start();

        tagline.setAlpha(0f);
        tagline.animate().alpha(0.7f).setDuration(1000).setStartDelay(1000).start();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            PreferenceManager pref = new PreferenceManager(this);
            Intent intent;
            if (!pref.isSetupComplete()) {
                intent = new Intent(SplashActivity.this, OnboardingActivity.class);
            } else {
                intent = new Intent(SplashActivity.this, UnlockActivity.class);
            }
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }, 3000);
    }
}