package com.example.mainactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class Familiebobla extends Activity {

    private EditText search;
    FamilieboblaSamtale fs;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familiebobla);

        endActivityAndGoBack(R.id.tilbakeBtn);
        goToNewSiteListener(R.id.leggTilSamtale, FamilieboblaNySamtale.class);

        setListenerOnBtns();

        search = (EditText) findViewById(R.id.search);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showElements();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setListenerOnBtns() {
        ConstraintLayout elementWraper = (ConstraintLayout) findViewById(R.id.leggTilElementer);
        final int children = elementWraper.getChildCount();

        for (int i = 0; i < children; i++) {
            Button btn = (Button) elementWraper.getChildAt(i);

            goToNewSiteListener(btn.getId(), FamilieboblaSamtale.class);
        }
    }

    private void showElements() {
        ConstraintLayout elementWraper = (ConstraintLayout) findViewById(R.id.leggTilElementer);
        final int children = elementWraper.getChildCount();

        for (int i = 0; i < children; i++) {
            Button btn = (Button) elementWraper.getChildAt(i);
            String txtOfBtn = btn.getText().toString().toLowerCase();

            if (!txtOfBtn.contains(search.getText().toString().toLowerCase()))
                btn.setVisibility(View.GONE);
            else {
                btn.setVisibility(View.VISIBLE);

                goToNewSiteListener(btn.getId(), FamilieboblaSamtale.class);
            }
        }
    }
}