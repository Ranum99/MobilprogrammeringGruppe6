package com.example.mainactivity;

import android.os.Bundle;

public class BursdagLeggTil extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bursdag_legg_til);

        endActivityAndGoBack(R.id.tilbakeBtn);
        endActivityAndGoBack(R.id.avbrytBtn);

    }
}