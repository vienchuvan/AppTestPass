package com.vaultpass.fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.demo.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.vaultpass.model.Password;
import com.vaultpass.security.CryptoManager;

public class PasswordActionBottomSheet extends BottomSheetDialogFragment {

    private final Password password;
    private final CryptoManager cryptoManager;

    public PasswordActionBottomSheet(Password password) {
        this.password = password;
        this.cryptoManager = new CryptoManager();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_password_actions, container, false);

        view.findViewById(R.id.btn_bs_copy_username).setOnClickListener(v -> copyToClipboard("Username", password.getUsername()));
        view.findViewById(R.id.btn_bs_copy_password).setOnClickListener(v -> {
            try {
                String decrypted = cryptoManager.decrypt(password.getPassword());
                copyToClipboard("Password", decrypted);
                dismiss();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Failed to decrypt", Toast.LENGTH_SHORT).show();
            }
        });

        // Other actions (Edit/Delete) can be added here with callbacks to Activity

        return view;
    }

    private void copyToClipboard(String label, String text) {
        ClipboardManager clipboard = (ClipboardManager) requireContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), label + " copied to clipboard", Toast.LENGTH_SHORT).show();
        }
    }
}