package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Slutter aktiviteten. Dersom man er på første aktivitet går man ut av appen.
    public void endActivityAndGoBack(int idBtn) {
        findViewById(idBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Åpner en ny aktivitet. Vil legge seg over forrige aktivitet
    public <T> void goToNewSite(int idBtn, final Class<T> classToUse) {

        findViewById(idBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), classToUse);
                startActivity(activity2Intent);
            }
        });
    }
}