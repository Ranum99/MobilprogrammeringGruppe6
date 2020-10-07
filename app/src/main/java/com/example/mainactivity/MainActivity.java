package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onskeliste);

        goToNewSite(R.id.endreMinListe, OnskelisteEndreListe.class);
        goToNewSite(R.id.buttonMor, OnskelisteForPerson.class);
    }

    // Slutter aktiviteten. Dersom man er på første aktivitet går man ut av appen.
    public void endActivityAndGoBack(int idBtn) {
        Button tilbakeBtn = findViewById(idBtn);
        tilbakeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Åpner en ny aktivitet. Vil legge seg over forrige aktivitet
    public <T> void goToNewSite(int idBtn, final Class<T> classToUse) {
        Button btn = findViewById(idBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), classToUse);
                startActivity(activity2Intent);
            }
        });
    }
}