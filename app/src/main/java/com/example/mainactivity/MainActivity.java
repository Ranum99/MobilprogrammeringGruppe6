package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavController controller = Navigation.findNavController(this, R.id.fragment);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);
        NavigationUI.setupWithNavController(bottomNavigation, controller);

        NavigationView navigationView = findViewById(R.id.NavigationView);
        NavigationUI.setupWithNavController(navigationView, controller);


    }


}