package com.vaultpass.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vaultpass.security.PasswordHasher;
import com.vaultpass.utils.PreferenceManager;

public class UnlockActivity extends AppCompatActivity {

    private TextInputEditText etPassword;
    private MaterialButton btnUnlock;
    private PreferenceManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);

        etPassword = findViewById(R.id.et_password);
        btnUnlock = findViewById(R.id.btn_unlock);
        pref = new PreferenceManager(this);

        btnUnlock.setOnClickListener(v -> {
            String input = etPassword.getText().toString();
            if (TextUtils.isEmpty(input)) {
                shakeView(etPassword);
                return;
            }

            String storedHash = pref.getMasterPasswordHash();
            if (PasswordHasher.checkPassword(input, storedHash)) {
                startActivity(new Intent(this, HomeActivity.class));
                finish();
            } else {
                handleWrongPassword();
            }
        });
    }

    private void handleWrongPassword() {
        shakeView(etPassword);
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(500);
        }
        Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
    }

    private void shakeView(android.view.View view) {
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        view.startAnimation(shake);
    }
}