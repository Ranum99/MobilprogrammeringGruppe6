package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Bundle;

public class ProfileTemp extends Activity {
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_temp);

        endActivityAndGoBack(R.id.tilbakeBtn);

        sharedPreferences = getSharedPreferences(User.SESSION, MODE_PRIVATE);
        System.out.println(sharedPreferences.getString(User.ID, null));
    }
}