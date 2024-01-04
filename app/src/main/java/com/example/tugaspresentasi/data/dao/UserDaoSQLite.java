package com.example.tugaspresentasi.data.dao;

import static com.example.tugaspresentasi.utils.PasswordUtils.hashPassword;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tugaspresentasi.config.DatabaseConfig;
import com.example.tugaspresentasi.data.models.User;

public class UserDaoSQLite implements UserDao {
    private final DatabaseConfig databaseConfig;
    public UserDaoSQLite(Context context) {
        databaseConfig = new DatabaseConfig(context);
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String hashedPassword) {
        // Implement this method using SQLite
        SQLiteDatabase db = databaseConfig.getReadableDatabase();

        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        Log.d("DatabaseConfig", "Executing query: " + query);

        Log.d("DatabaseConfig", "Username: " + username);
        Log.d("DatabaseConfig", "Hashed password: " + hashedPassword);

        Cursor cursor = db.query("users", null, "username = ? AND password = ?",
                new String[]{username, hashedPassword}, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            user = new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
        }

        cursor.close();
        db.close();
        return user;
    }

    @Override
    public boolean countUsersWithUsername(String username) {
        // Implement this method using SQLite
        SQLiteDatabase db = databaseConfig.getReadableDatabase();
        Cursor cursor = db.query("users", null, "username = ?",
                new String[]{username}, null, null, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        db.close();
        return exists;
    }

    @Override
    public long insert(User user) {
        // Implement this method using SQLite
        SQLiteDatabase db = databaseConfig.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("email", user.getEmail());
        values.put("password", user.getPassword());
        values.put("username", user.getUsername());

        /* return db.insert("users", null, values); */

        long newRowId = db.insert("users", null, values);
        db.close();
        return newRowId;
    }
}
