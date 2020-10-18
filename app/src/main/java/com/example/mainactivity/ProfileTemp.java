package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class ProfileTemp extends Activity {
    SharedPreferences sharedPreferences;

    private TextView userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        endActivityAndGoBack(R.id.tilbakeBtn);

        goToNewSiteListener(R.id.btnEndreProfil, ProfileChangeTemp.class);

        sharedPreferences = getSharedPreferences(User.SESSION, MODE_PRIVATE);

        userID = (TextView) findViewById(R.id.userID);
        userID.setText(userID.getText() + sharedPreferences.getString(User.ID, null));
    }
}