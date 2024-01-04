package com.example.tugaspresentasi.data.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Patterns;

import com.example.tugaspresentasi.config.DatabaseConfig;
import com.example.tugaspresentasi.data.dao.UserDao;
import com.example.tugaspresentasi.data.models.User;
import com.example.tugaspresentasi.utils.PasswordUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserRepository {
    private final DatabaseConfig databaseConfig;
    private final UserDao userDao;

    public UserRepository(UserDao userDao, Context context) {
        this.userDao = userDao;
        this.databaseConfig = new DatabaseConfig(context);
    }

    public User userLogin(String username, String password) {
        String hashedPassword = PasswordUtils.hashPassword(password);
        return userDao.findUserByUsernameAndPassword(username, hashedPassword);
    }

    public boolean isUsernameRegistered(String username) {
        return userDao.countUsersWithUsername(username);
    }

    public boolean registerUser(String email, String password, String username) {
        Log.d("UserRepository", "Registering user with username: " + username + ", email: " + email);

        if (isUsernameRegistered(username)) {
            throw new IllegalArgumentException("Username is already registered");
        } else if (username == null || username.trim().isEmpty()){
            throw new IllegalArgumentException("Username is empty");
        } else if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid Email");
        } else if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password is empty");
        }

        String hashedPassword = PasswordUtils.hashPassword(password);
        User user = new User(email, hashedPassword, username);
        long newRowId = userDao.insert(user);

        if (newRowId == -1) {
            Log.d("UserRepository", "User registration failed");
            return false;
        } else {
            Log.d("UserRepository", "User registered successfully: " + user.getUsername());
            return true;
        }
    }
}
