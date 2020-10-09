package com.example.mainactivity;

import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToNewSite(R.id.goToHandleliste, HandlelisteTemp.class);
        goToNewSite(R.id.goToKalender, KalenderTemp.class);
        goToNewSite(R.id.goToMatplan, Matplan.class);
        goToNewSite(R.id.goToFamiliebobla, Familiebobla.class);
        goToNewSite(R.id.goToBursdager, Bursdager.class);
        goToNewSite(R.id.goToOnskeliste, Onskeliste.class);

    }
}