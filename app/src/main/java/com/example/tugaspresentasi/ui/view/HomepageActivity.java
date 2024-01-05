package com.example.tugaspresentasi.ui.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.tugaspresentasi.R;
import com.example.tugaspresentasi.ui.view.fragment.FavoriteFragment;
import com.example.tugaspresentasi.ui.view.fragment.HomeFragment;
import com.example.tugaspresentasi.ui.view.fragment.NotificationFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomepageActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.light_blue));

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        // Set up the listener for NavigationView
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment selectedFragment = null;
            String title = "";

            if (id == R.id.nav_favorite) {
                selectedFragment = new FavoriteFragment();
                title = "Dashboard";
            } else if (id == R.id.nav_notifications) {
                selectedFragment = new NotificationFragment();
                title = "Notifications";
            } else if (id == R.id.nav_logout) {
                logout();
                return true;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportActionBar().setTitle(title);
            }
            return true;
        });

        // Set up the listener for BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment selectedFragment = null;
            String title = "";

            if (id == R.id.navigation_home) {
                selectedFragment = new HomeFragment();
                title = "Home";
            } else if (id == R.id.navigation_favorite) {
                selectedFragment = new FavoriteFragment();
                title = "Dashboard";
            } else if (id == R.id.navigation_notifications) {
                selectedFragment = new NotificationFragment();
                title = "Notifications";
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                getSupportActionBar().setTitle(title);
            }
            return true;
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }

    private void logout() {
        // Clear session data
        SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Navigate back to the login screen
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
