package com.example.mainactivity;

import android.os.Bundle;

public class Onskeliste extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onskeliste);

        endActivityAndGoBack(R.id.tilbakeBtn);

        goToNewSiteListener(R.id.endreMinListe, OnskelisteEndreListe.class);
        goToNewSiteListener(R.id.buttonMor, OnskelisteForPerson.class);
        goToNewSiteListener(R.id.buttonFar, OnskelisteForPerson.class);
        goToNewSiteListener(R.id.buttonBarn1, OnskelisteForPerson.class);
        goToNewSiteListener(R.id.buttonBarn2, OnskelisteForPerson.class);
    }
}