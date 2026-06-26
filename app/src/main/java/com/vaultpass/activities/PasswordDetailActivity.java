package com.vaultpass.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.R;
import com.vaultpass.model.Password;
import com.vaultpass.repository.PasswordRepository;
import com.vaultpass.security.CryptoManager;

public class PasswordDetailActivity extends AppCompatActivity {

    private TextView tvTitle, tvUsername, tvPassword, tvNotes;
    private ImageButton btnToggleVisibility, btnCopy;
    private Password password;
    private boolean isPasswordVisible = false;
    private CryptoManager cryptoManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);

        int passwordId = getIntent().getIntExtra("password_id", -1);
        if (passwordId == -1) {
            finish();
            return;
        }

        PasswordRepository repository = new PasswordRepository(this);
        password = repository.getPasswordById(passwordId);
        cryptoManager = new CryptoManager();

        tvTitle = findViewById(R.id.tv_title);
        tvUsername = findViewById(R.id.tv_username);
        tvPassword = findViewById(R.id.tv_password);
        tvNotes = findViewById(R.id.tv_notes);
        btnToggleVisibility = findViewById(R.id.btn_toggle_visibility);
        btnCopy = findViewById(R.id.btn_copy);

        displayDetails();

        btnToggleVisibility.setOnClickListener(v -> togglePasswordVisibility());
        btnCopy.setOnClickListener(v -> copyPasswordToClipboard());
    }

    private void displayDetails() {
        if (password != null) {
            tvTitle.setText(password.getTitle());
            tvUsername.setText(password.getUsername());
            tvNotes.setText(password.getNotes());
        }
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            tvPassword.setText("••••••••");
            isPasswordVisible = false;
        } else {
            try {
                String decrypted = cryptoManager.decrypt(password.getPassword());
                tvPassword.setText(decrypted);
                isPasswordVisible = true;
            } catch (Exception e) {
                Toast.makeText(this, "Decryption failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void copyPasswordToClipboard() {
        try {
            String decrypted = cryptoManager.decrypt(password.getPassword());
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("VaultPass Password", decrypted);
            if (clipboard != null) {
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Password copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to copy password", Toast.LENGTH_SHORT).show();
        }
    }
}