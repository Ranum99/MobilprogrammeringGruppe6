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

        Button tilbakeBtn = findViewById(R.id.tilbakeBtn);
        tilbakeBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        goToNewSite(R.id.endreMinListe, OnskelisteEndreListe.class);
        goToNewSite(R.id.buttonMor, OnskelisteForPerson.class);




    }

    private <T> void goToNewSite(int idBtn, final Class<T> classToUse) {
        Button btn = findViewById(idBtn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), classToUse);
                startActivity(activity2Intent);
            }
        });
    }
}