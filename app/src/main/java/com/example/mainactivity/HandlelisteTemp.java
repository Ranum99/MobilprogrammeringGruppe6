package com.example.mainactivity;

import android.os.Bundle;

public class HandlelisteTemp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handleliste_temp);

        endActivityAndGoBack(R.id.tilbakeBtn);

        goToNewSite(R.id.btnHandleliste1, HandlelisteEnListeTemp.class);
        goToNewSite(R.id.btnHandleliste2, HandlelisteEnListeTemp.class);
        goToNewSite(R.id.btnHandleliste3, HandlelisteEnListeTemp.class);
        goToNewSite(R.id.btnHandleliste4, HandlelisteEnListeTemp.class);
        goToNewSite(R.id.opprettNyListe, HandlelisteOpprettListeTemp.class);
    }
}