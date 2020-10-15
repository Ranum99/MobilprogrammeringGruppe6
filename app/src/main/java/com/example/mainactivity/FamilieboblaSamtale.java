package com.example.mainactivity;

import android.os.Bundle;
import android.widget.TextView;

public class FamilieboblaSamtale extends Activity {

    private int userSamtaleId;
    private String userSamtale;

    private TextView samtaleTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familiebobla_samtale);

        endActivityAndGoBack(R.id.tilbakeBtn);

        samtaleTitle = (TextView) findViewById(R.id.tittel);
        samtaleTitle.setText(userSamtale);
    }

    public void setUserSamtaleId(int userSamtaleId) {
        this.userSamtaleId = userSamtaleId;
    }

    public void setUserSamtale(String userSamtale) {
        this.userSamtale = userSamtale;
    }
}