package com.example.mainactivity;

import android.os.Bundle;

public class Bursdager extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bursdager);

        endActivityAndGoBack(R.id.tilbakeBtn);
    }
}