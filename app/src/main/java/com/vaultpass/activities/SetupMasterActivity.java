package com.vaultpass.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.demo.R;
import com.vaultpass.utils.AnimationUtils;
import android.view.View;

public class SetupMasterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_master);
        
        View root = findViewById(R.id.setup_root);
        if (root != null) {
            AnimationUtils.scaleUp(root);
        }
    }
}
