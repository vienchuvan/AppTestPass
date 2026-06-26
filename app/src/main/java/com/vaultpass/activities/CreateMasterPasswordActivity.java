package com.vaultpass.activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vaultpass.security.PasswordHasher;
import com.vaultpass.utils.PreferenceManager;

public class CreateMasterPasswordActivity extends AppCompatActivity {

    private TextInputEditText etPassword, etConfirmPassword;
    private ProgressBar strengthMeter;
    private TextView tvStrengthText;
    private MaterialButton btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_master_password);

        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        strengthMeter = findViewById(R.id.password_strength_meter);
        tvStrengthText = findViewById(R.id.tv_strength_text);
        btnCreate = findViewById(R.id.btn_create);

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateStrengthMeter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnCreate.setOnClickListener(v -> {
            String pass = etPassword.getText().toString();
            String confirm = etConfirmPassword.getText().toString();

            if (pass.isEmpty()) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!pass.equals(confirm)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (pass.length() < 8) {
                Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
                return;
            }

            // Save Master Password Hash
            PreferenceManager pref = new PreferenceManager(this);
            pref.setMasterPasswordHash(PasswordHasher.hashPassword(pass));
            pref.setSetupComplete(true);

            // Go to Home
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        });
    }

    private void updateStrengthMeter(String password) {
        int strength = calculateStrength(password);
        strengthMeter.setProgress(strength);

        if (strength < 30) {
            tvStrengthText.setText("Strength: Weak");
            strengthMeter.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.strength_weak)));
        } else if (strength < 60) {
            tvStrengthText.setText("Strength: Medium");
            strengthMeter.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.strength_medium)));
        } else if (strength < 90) {
            tvStrengthText.setText("Strength: Strong");
            strengthMeter.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.strength_strong)));
        } else {
            tvStrengthText.setText("Strength: Great");
            strengthMeter.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.strength_great)));
        }
    }

    private int calculateStrength(String password) {
        if (password.length() == 0) return 0;
        int score = 0;
        if (password.length() >= 8) score += 25;
        if (password.matches(".*[A-Z].*")) score += 25;
        if (password.matches(".*[a-z].*")) score += 20;
        if (password.matches(".*[0-9].*")) score += 20;
        if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) score += 10;
        return Math.min(score, 100);
    }
}