package com.example.mainactivity;

import android.os.Bundle;

public class KalenderTemp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender_temp);

        endActivityAndGoBack(R.id.tilbakeBtn);

        goToNewSiteListener(R.id.btnRedigerKalender, KalenderEndreTemp.class);
        goToNewSiteListener(R.id.btnSlettKalender, KalenderSlettTemp.class);
    }
}