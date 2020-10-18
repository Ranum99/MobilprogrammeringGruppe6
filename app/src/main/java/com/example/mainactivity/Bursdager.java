package com.example.mainactivity;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

public class Bursdager extends Activity {
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bursdager);
        recyclerView = findViewById(R.id.BirthdayListRecycler);

        endActivityAndGoBack(R.id.tilbakeBtn);
        goToNewSiteListener(R.id.AddBirthday, BursdagLeggTil.class);

    }



}