package com.example.mainactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Onskeliste extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onskeliste);

        goToNewSite(R.id.endreMinListe, OnskelisteEndreListe.class);
        goToNewSite(R.id.buttonMor, OnskelisteForPerson.class);
        endActivityAndGoBack(R.id.tilbakeBtn);
    }
}