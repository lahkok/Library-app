package com.example.tugaspresentasi.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tugaspresentasi.R;
import com.example.tugaspresentasi.data.dao.UserDao;
import com.example.tugaspresentasi.data.dao.UserDaoSQLite;
import com.example.tugaspresentasi.data.repositories.UserRepository;
import com.example.tugaspresentasi.factories.RegistrationViewModelFactory;
import com.example.tugaspresentasi.ui.viewmodels.RegistrationViewModel;

public class RegisterActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button registerButton;
    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Context context = getApplicationContext();
        UserDao userDao = new UserDaoSQLite(context);
        UserRepository userRepository = new UserRepository(userDao, context);
        RegistrationViewModelFactory factory = new RegistrationViewModelFactory(userRepository);
        viewModel = new ViewModelProvider(this, factory).get(RegistrationViewModel.class);

        usernameEditText = findViewById(R.id.usernameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                viewModel.registerUser(email, password, username);
            }
        });

        viewModel.getRegistrationSuccess().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean success) {
                if (success) {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        viewModel.getErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
