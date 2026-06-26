package com.vaultpass.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;
import com.vaultpass.model.Password;

import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.ViewHolder> {

    private final List<Password> passwords;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Password password);
        void onItemLongClick(Password password);
    }

    public PasswordAdapter(List<Password> passwords, OnItemClickListener listener) {
        this.passwords = passwords;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_password, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Password password = passwords.get(position);
        holder.tvAccountName.setText(password.getTitle());
        holder.tvUsername.setText(password.getUsername());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(password));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(password);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return passwords.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAccountName, tvUsername;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAccountName = itemView.findViewById(R.id.tv_account_name);
            tvUsername = itemView.findViewById(R.id.tv_username);
        }
    }
}