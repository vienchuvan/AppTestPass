package com.vaultpass.repository;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vaultpass.database.DatabaseHelper;
import com.vaultpass.model.Password;

import java.util.ArrayList;
import java.util.List;

public class PasswordRepository {
    private final DatabaseHelper dbHelper;

    public PasswordRepository(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long addPassword(Password password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TITLE, password.getTitle());
        values.put(DatabaseHelper.COLUMN_USERNAME, password.getUsername());
        values.put(DatabaseHelper.COLUMN_PASSWORD, password.getPassword());
        values.put(DatabaseHelper.COLUMN_EMAIL, password.getEmail());
        values.put(DatabaseHelper.COLUMN_URL, password.getUrl());
        values.put(DatabaseHelper.COLUMN_CATEGORY, password.getCategory());
        values.put(DatabaseHelper.COLUMN_IS_FAVORITE, password.isFavorite() ? 1 : 0);
        values.put(DatabaseHelper.COLUMN_NOTES, password.getNotes());
        
        long id = db.insert(DatabaseHelper.TABLE_PASSWORDS, null, values);
        db.close();
        return id;
    }

    @SuppressLint("Range")
    public List<Password> getAllPasswords() {
        List<Password> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PASSWORDS, null, null, null, null, null, DatabaseHelper.COLUMN_TITLE + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Password p = new Password();
                p.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
                p.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE)));
                p.setUsername(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME)));
                p.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD)));
                p.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)));
                p.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_URL)));
                p.setCategory(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)));
                p.setFavorite(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_FAVORITE)) == 1);
                p.setNotes(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOTES)));
                p.setCreatedAt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CREATED_AT)));
                p.setUpdatedAt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UPDATED_AT)));
                list.add(p);
            } while (cursor.moveToNext());
        }
        if (cursor != null) cursor.close();
        db.close();
        return list;
    }

    @SuppressLint("Range")
    public Password getPasswordById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_PASSWORDS, null, DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null);
        
        Password p = null;
        if (cursor != null && cursor.moveToFirst()) {
            p = new Password();
            p.setId(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)));
            p.setTitle(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE)));
            p.setUsername(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME)));
            p.setPassword(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_PASSWORD)));
            p.setEmail(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)));
            p.setUrl(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_URL)));
            p.setCategory(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CATEGORY)));
            p.setFavorite(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_FAVORITE)) == 1);
            p.setNotes(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NOTES)));
            p.setCreatedAt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_CREATED_AT)));
            p.setUpdatedAt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_UPDATED_AT)));
        }
        if (cursor != null) cursor.close();
        db.close();
        return p;
    }
}