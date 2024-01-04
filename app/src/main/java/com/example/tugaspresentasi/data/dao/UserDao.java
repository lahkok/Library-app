package com.example.tugaspresentasi.data.dao;

import static com.example.tugaspresentasi.utils.PasswordUtils.hashPassword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tugaspresentasi.config.DatabaseConfig;
import com.example.tugaspresentasi.data.models.User;

public interface UserDao {
    User findUserByUsernameAndPassword(String username, String password);
    boolean countUsersWithUsername(String username);
    long insert(User user);
}

