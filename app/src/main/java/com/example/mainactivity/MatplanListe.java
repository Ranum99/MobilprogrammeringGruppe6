package com.example.mainactivity;

import android.os.Bundle;

public class MatplanListe extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matplan_liste);

        endActivityAndGoBack(R.id.tilbakeBtn);

        goToNewSiteListener(R.id.AddFoodPlan, MatplanLeggTil.class);


    }
}
