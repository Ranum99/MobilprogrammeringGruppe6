package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileActivity extends Activity {
    SharedPreferences sharedPreferences;
    Button Logout;
    private TextView userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Går tilbake
        endActivityAndGoBack(R.id.tilbakeBtn);

        //Går til endre profil siden
        goToNewSiteListener(R.id.btnEndreProfil, ProfileChangeTemp.class);

        sharedPreferences = getSharedPreferences(User.SESSION, MODE_PRIVATE);
        userID = (TextView) findViewById(R.id.userID);
        userID.setText(userID.getText() + sharedPreferences.getString(User.ID, null));

        //Logger ut bruker
        Logout = (Button) findViewById(R.id.btnLoggUt);

        Intent intent = getIntent();
        String string = intent.getStringExtra("message");
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Confirmation Popup!").
                        setMessage("Are you sure that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
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
