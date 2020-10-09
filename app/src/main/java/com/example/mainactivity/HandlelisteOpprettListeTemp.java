package com.example.mainactivity;

import android.os.Bundle;

public class HandlelisteOpprettListeTemp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handleliste_opprett_liste_temp);

        endActivityAndGoBack(R.id.tilbakeBtn);
    }
}