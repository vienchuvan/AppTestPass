package com.vaultpass.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.vaultpass.adapter.PasswordAdapter;
import com.vaultpass.fragments.PasswordActionBottomSheet;
import com.vaultpass.model.Password;
import com.vaultpass.repository.PasswordRepository;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rvPasswords;
    private TextView tvVaultCount;
    private PasswordRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        repository = new PasswordRepository(this);
        rvPasswords = findViewById(R.id.rv_passwords);
        tvVaultCount = findViewById(R.id.tv_vault_count);
        ExtendedFloatingActionButton fabAdd = findViewById(R.id.fab_add);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        rvPasswords.setLayoutManager(new LinearLayoutManager(this));

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AddPasswordActivity.class));
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.action_settings) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                return true;
            }
            return false;
        });

        loadPasswords();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPasswords();
    }

    private void loadPasswords() {
        List<Password> passwords = repository.getAllPasswords();
        tvVaultCount.setText(passwords.size() + " Secure items");
        
        if (passwords.isEmpty()) {
            rvPasswords.setVisibility(View.GONE);
        } else {
            rvPasswords.setVisibility(View.VISIBLE);
            PasswordAdapter adapter = new PasswordAdapter(passwords, new PasswordAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Password password) {
                    Intent intent = new Intent(HomeActivity.this, PasswordDetailActivity.class);
                    intent.putExtra("password_id", password.getId());
                    startActivity(intent);
                }

                @Override
                public void onItemLongClick(Password password) {
                    PasswordActionBottomSheet bottomSheet = new PasswordActionBottomSheet(password);
                    bottomSheet.show(getSupportFragmentManager(), "PasswordActions");
                }
            });
            rvPasswords.setAdapter(adapter);
        }
    }
}