package com.example.mainactivity;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends Activity {

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
        goToNewSiteListener(R.id.Info, ProfileTemp.class);


        TabLayout tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

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