package com.example.mainactivity;

import androidx.annotation.RequiresApi;
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
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom;
    private Toolbar toolbar;
    private NavigationView navigation;
    private TextView navn;
    private TextView id;
    private SharedPreferences sharedPreferences;
    private Database database;
    NavController controller;
    AppBarConfiguration appBarConfiguration;


    // to check if we are connected to Network
    boolean isConnected = true;

    // to check if we are monitoring Network
    boolean monitoringConnectivity = false;

    private static final String TAG = "LOGINACTIVITY";

    ConnectivityManager.NetworkCallback connectivityCallback;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isNetworkAvailable() == false) {
            Snackbar.make(findViewById(R.id.Main), "Du har ikke nett", Snackbar.LENGTH_SHORT).show();
        }

        database = new Database(this);
        sharedPreferences = this.getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        controller = Navigation.findNavController(this, R.id.fragment);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();

        connectivityCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                isConnected = true;
                Log.d(TAG, "INTERNET CONNECTED");
            }

            @Override
            public void onLost(Network network) {
                isConnected = false;
                Log.d(TAG, "INTERNET LOST");
            }
        };

        checkConnectivity();

    }

    public void setText(String newFamilyName) {
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

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkConnectivity() {
        // here we are getting the connectivity service from connectivity manager
        final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(
                Context.CONNECTIVITY_SERVICE);

        // Getting network Info
        // give Network Access Permission in Manifest
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        // isConnected is a boolean variable
        // here we check if network is connected or is getting connected
        isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();

        if (!isConnected) {
            // SHOW ANY ACTION YOU WANT TO SHOW
            // WHEN WE ARE NOT CONNECTED TO INTERNET/NETWORK
            Log.d(TAG, " NO NETWORK!");
            // if Network is not connected we will register a network callback to  monitor network
            connectivityManager.registerNetworkCallback(
                    new NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .build(), connectivityCallback);
            monitoringConnectivity = true;
        }

    }

}