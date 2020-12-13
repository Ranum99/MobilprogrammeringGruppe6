package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom;
    private Toolbar toolbar;
    private NavigationView navigation;
    private static TextView navn;
    private TextView id;
    private SharedPreferences sharedPreferences;
    private Database database;
    NavController controller;
    AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = new Database(this);
        sharedPreferences = this.getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        controller = Navigation.findNavController(this, R.id.fragment);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bottom = findViewById(R.id.bottomNavigation);
        navigation = findViewById(R.id.navDrawer);
        DrawerLayout drawer = findViewById(R.id.DrawerLayout);

        appBarConfiguration =
                new AppBarConfiguration.Builder(
                        R.id.matplanFragment,
                        R.id.kalenderFragment,
                        R.id.onskelisteFragment,
                        R.id.handlelisteFragment,
                        R.id.familieboblaFragment,
                        R.id.profilFragment,
                        R.id.gruppeinformasjonFragment,
                        R.id.settingsFragment ).setDrawerLayout(drawer)
                        .build();

        NavigationUI.setupActionBarWithNavController(this, controller, appBarConfiguration);
        NavigationUI.setupWithNavController(navigation, controller);
        getSupportActionBar().setDisplayHomeAsUpEnabled (true);



        //NavigationUI.setupWithNavController(toolbar, controller, appBarConfiguration);
        NavigationUI.setupWithNavController(bottom, controller);



        String familieNavnet = getFamilyName();
        View header = navigation.getHeaderView(0);
        navn = header.findViewById(R.id.DrawerFamilyName);
        id = header.findViewById(R.id.DrawerFamilyId);
        navn.setText(familieNavnet);
        id.setText("Familie-ID: " + sharedPreferences.getString(User.FAMILIE, null));
    }

    public static void setText(String newFamilyName) {
        navn.setText(newFamilyName);
    }

    private String getFamilyName() {
        String name = "";
        Cursor familieNavnetQuery = database.getData(Database.TABLE_FAMILY, Integer.parseInt(sharedPreferences.getString(User.FAMILIE, null)));
        while(familieNavnetQuery.moveToNext()) {
            name = familieNavnetQuery.getString(1);
        }
        return name;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(controller, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}