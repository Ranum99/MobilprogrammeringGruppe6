package com.example.mainactivity;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToNewSite(R.id.goToHandleliste, HandlelisteTemp.class);
        goToNewSite(R.id.goToKalender, KalenderTemp.class);
        goToNewSite(R.id.goToMatplan, MatplanListe.class);
        goToNewSite(R.id.goToFamiliebobla, Familiebobla.class);
        goToNewSite(R.id.goToBursdager, Bursdager.class);
        goToNewSite(R.id.goToOnskeliste, Onskeliste.class);

        System.out.println("123");

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