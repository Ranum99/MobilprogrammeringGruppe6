package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Profile extends Activity {
    SharedPreferences sharedPreferences;
    Button Logout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Går tilbake
        endActivityAndGoBack(R.id.tilbakeBtn);

        //Går til endre profil siden
        goToNewSiteListener(R.id.btnEndreProfil, ProfileChange.class);

        sharedPreferences = getSharedPreferences(User.SESSION, MODE_PRIVATE);
        TextView userID = (TextView) findViewById(R.id.userID);
        userID.setText(userID.getText() + sharedPreferences.getString(User.ID, null));



        //Logger ut bruker
        Logout = (Button) findViewById(R.id.btnLoggUt);

        Intent intent = getIntent();
        String string = intent.getStringExtra("message");
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Confirmation Popup!").
                        setMessage("Are you sure that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Tømmer sharedPreferences
                                SharedPreferences preferences = getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.clear();
                                editor.apply();
                                finish();

                                Intent intent1 = new Intent(getApplicationContext(), Login.class);
                                startActivity(intent1);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });
    }
}
