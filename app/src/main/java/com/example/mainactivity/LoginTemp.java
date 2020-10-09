package com.example.mainactivity;

import android.os.Bundle;

public class LoginTemp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_temp);


        goToNewSite(R.id.signUpBtn, SignupTemp.class);
        goToNewSite(R.id.btnLogin, MainActivity.class);
    }
}