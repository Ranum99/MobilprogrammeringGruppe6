package com.example.mainactivity;

import android.os.Bundle;

public class SignupTemp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_temp);

        endActivityAndGoBack(R.id.tilbakeBtn);
    }
}