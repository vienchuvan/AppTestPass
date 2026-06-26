package com.vaultpass.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vaultpass.model.Password;
import com.vaultpass.repository.PasswordRepository;
import com.vaultpass.security.CryptoManager;

public class AddPasswordActivity extends AppCompatActivity {

    private TextInputEditText etTitle, etUsername, etPassword, etEmail, etUrl, etNotes;
    private PasswordRepository repository;
    private CryptoManager cryptoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);

        repository = new PasswordRepository(this);
        cryptoManager = new CryptoManager();

        etTitle = findViewById(R.id.et_title);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password_field);
        etEmail = findViewById(R.id.et_email);
        etUrl = findViewById(R.id.et_url);
        etNotes = findViewById(R.id.et_notes);
        MaterialButton btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(v -> savePassword());
    }

    private void savePassword() {
        String title = etTitle.getText().toString();
        String username = etUsername.getText().toString();
        String rawPassword = etPassword.getText().toString();

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(rawPassword)) {
            Toast.makeText(this, "Title and Password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String encryptedPassword = cryptoManager.encrypt(rawPassword);
            
            Password p = new Password();
            p.setTitle(title);
            p.setUsername(username);
            p.setPassword(encryptedPassword);
            p.setEmail(etEmail.getText().toString());
            p.setUrl(etUrl.getText().toString());
            p.setNotes(etNotes.getText().toString());
            p.setCategory("General");
            p.setFavorite(false);

            repository.addPassword(p);
            Toast.makeText(this, "Password saved securely", Toast.LENGTH_SHORT).show();
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Encryption failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}