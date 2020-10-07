package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OnskelisteEndreListe extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onskeliste_endre_liste);

        endActivityAndGoBack(R.id.tilbakeBtn);
    }
}