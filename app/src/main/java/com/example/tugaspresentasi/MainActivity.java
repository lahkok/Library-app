package com.example.tugaspresentasi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.tugaspresentasi.ui.view.ForgotPasswordActivity;
import com.example.tugaspresentasi.ui.view.HomepageActivity;
import com.example.tugaspresentasi.ui.view.LoginActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);

        // Retrieve the user's username
        String savedUsername = sharedPreferences.getString("username", "");

        if (!savedUsername.equals("")) {
            // The user is already logged in, navigate to the homepage
            Intent intent = new Intent(this, HomepageActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        Button move = findViewById(R.id.move);

        move.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}