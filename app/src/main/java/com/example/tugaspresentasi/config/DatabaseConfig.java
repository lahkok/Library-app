package com.example.tugaspresentasi.config;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.tugaspresentasi.data.models.User;

public class DatabaseConfig extends SQLiteOpenHelper {
    private static final String DB_NAME = "tugasmobile.db";
    private static final int DB_VERSION = 1;

    private static DatabaseConfig instance = null;

    public DatabaseConfig(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DatabaseConfig getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseConfig(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users ( " +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "password TEXT NOT NULL)");

        db.execSQL("CREATE TABLE books (" +
                "book_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "book_title TEXT NOT NULL," +
                "book_author TEXT NOT NULL," +
                "book_description TEXT," +
                "cover_image_uri TEXT," +
                "user_id INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES users(user_id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "users");
        db.execSQL("DROP TABLE IF EXISTS " + "books");
        // Create tables again
        onCreate(db);
    }

    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

}

