package com.example.mainactivity;

import android.os.Bundle;

public class KalenderEndreTemp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender_endre_temp);

        endActivityAndGoBack(R.id.tilbakeBtn);
    }
}