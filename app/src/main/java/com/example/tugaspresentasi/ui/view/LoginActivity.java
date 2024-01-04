package com.example.tugaspresentasi.ui.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.tugaspresentasi.R;
import com.example.tugaspresentasi.data.dao.UserDao;
import com.example.tugaspresentasi.data.dao.UserDaoSQLite;
import com.example.tugaspresentasi.data.repositories.UserRepository;
import com.example.tugaspresentasi.factories.LoginViewModelFactory;
import com.example.tugaspresentasi.ui.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        TextView forgotPassword = findViewById(R.id.forgotPassword);
        TextView registerTextView = findViewById(R.id.register);

        Context context = getApplicationContext();
        UserDao userDao = new UserDaoSQLite(context);
        UserRepository userRepository = new UserRepository(userDao, context);
        LoginViewModelFactory factory = new LoginViewModelFactory(userRepository);
        viewModel = new ViewModelProvider(this, factory).get(LoginViewModel.class);

        observeLiveData();

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                viewModel.login(username, password);
            } else {
                Toast.makeText(LoginActivity.this, "Please enter a username and password", Toast.LENGTH_SHORT).show();
            }
        });

        forgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });

        registerTextView.setOnClickListener(v -> viewModel.navigateToRegister());
    }

    private void observeLiveData() {
        viewModel.getLoginState().observe(this, this::handleLoginState);
        viewModel.getNavigateToHomeEvent().observe(this, aVoid -> navigateToHome());
        viewModel.getNavigateToRegisterEvent().observe(this, aVoid -> navigateToRegister());
    }

    private void handleLoginState(LoginViewModel.LoginState loginState) {
        if (loginState instanceof LoginViewModel.LoginState.Success) {
            // Store the username in Shared Preferences
            SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();

            String username = usernameEditText.getText().toString();
            myEdit.putString("username", username);

            int userId = ((LoginViewModel.LoginState.Success) loginState).getUserId(); // Get the userId from the LoginState
            myEdit.putInt("userId", userId);

            myEdit.apply();

            navigateToHome();
        } else if (loginState instanceof LoginViewModel.LoginState.Error) {
            String errorMessage = ((LoginViewModel.LoginState.Error) loginState).getErrorMessage();
            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToHome() {
        Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
        startActivity(intent);
    }

    private void navigateToRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
