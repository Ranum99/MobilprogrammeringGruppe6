package com.example.mainactivity;

import android.os.Bundle;

public class Familiebobla extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familiebobla);

        endActivityAndGoBack(R.id.tilbakeBtn);

        goToNewSite(R.id.button1, FamilieboblaSamtale.class);
        goToNewSite(R.id.button2, FamilieboblaSamtale.class);
        goToNewSite(R.id.button3, FamilieboblaSamtale.class);
    }
}