package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class Activity extends AppCompatActivity {

    // Slutter aktiviteten. Dersom man er på første aktivitet går man ut av appen.
    public void endActivityAndGoBack(int idBtn) {
        findViewById(idBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    // Åpner en ny aktivitet. Vil legge seg over forrige aktivitet
    public <T> void goToNewSiteListener(int idBtn, final Class<T> classToUse) {
        findViewById(idBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), classToUse);
                startActivity(activity2Intent);
            }
        });
    }

    // Åpner en ny aktivitet. Vil legge seg over forrige aktivitet
    public <T> void goToNewSiteListener(int idBtn, final Class<T> classToUse, final boolean closeActivity) {
        findViewById(idBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(getApplicationContext(), classToUse);
                if (closeActivity)
                    finish();
                startActivity(activity2Intent);
            }
        });
    }

    public void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}