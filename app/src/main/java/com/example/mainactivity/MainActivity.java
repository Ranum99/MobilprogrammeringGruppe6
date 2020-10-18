package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends Activity {
    Button Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToNewSiteListener(R.id.goToHandleliste, HandlelisteTemp.class);
        goToNewSiteListener(R.id.goToKalender, KalenderTemp.class);
        goToNewSiteListener(R.id.goToMatplan, MatplanListe.class);
        goToNewSiteListener(R.id.goToFamiliebobla, Familiebobla.class);
        goToNewSiteListener(R.id.goToBursdager, Bursdager.class);
        goToNewSiteListener(R.id.goToOnskeliste, Onskeliste.class);

        Logout = (Button) findViewById(R.id.btnLogOut);

        Intent intent = getIntent();
        String string = intent.getStringExtra("message");
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Confirmation Popup!").
                        setMessage("Are you sure that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {


                                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent1);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });

        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                goToNewSiteListener(R.id.goToProfile, ProfileActivity.class);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

    });


        /*Mangler:
        * Kalender - legg til
        * Matplan  - rediger
        * Bursdag  - rediger*/
    }
}