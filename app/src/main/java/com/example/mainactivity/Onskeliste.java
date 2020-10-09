package com.example.mainactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Onskeliste extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onskeliste);

        endActivityAndGoBack(R.id.tilbakeBtn);

        goToNewSite(R.id.endreMinListe, OnskelisteEndreListe.class);
        goToNewSite(R.id.buttonMor, OnskelisteForPerson.class);
        goToNewSite(R.id.buttonFar, OnskelisteForPerson.class);
        goToNewSite(R.id.buttonBarn1, OnskelisteForPerson.class);
        goToNewSite(R.id.buttonBarn2, OnskelisteForPerson.class);
    }
}