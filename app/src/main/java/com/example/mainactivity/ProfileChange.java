package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ProfileChange extends Activity{
    Database database;
    SharedPreferences sharedPreferences;

    private EditText endreNavn, endreBursdag, endreEmail, endreMobilnr;
    private Button btnLagre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Velger hvilken XML fil som skal brukes
        setContentView(R.layout.activity_profile_change);

        // Går ut av aktiviteten
        endActivityAndGoBack(R.id.tilbakeBtn);
        endActivityAndGoBack(R.id.avbrytBtn);

        // Henter informasjon om brukeren
        sharedPreferences = getSharedPreferences(User.SESSION, MODE_PRIVATE);

        //userID = (TextView) findViewById(R.id.userID);
        //userID.setText(userID.getText() + sharedPreferences.getString(User.ID, null));

        endreNavn = (EditText) findViewById(R.id.endreProfilNavn);

        endreBursdag = (EditText) findViewById(R.id.endreProfilBursdag);

        endreEmail = (EditText) findViewById(R.id.endreProfilEmail);

        endreMobilnr = (EditText) findViewById(R.id.profilMobilnr);

        setUserData();

        btnLagre = (Button) findViewById(R.id.btnSend);
        btnLagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = endreNavn.getText().toString();
                String newBirthday = endreBursdag.getText().toString();
                String newEmail = endreEmail.getText().toString();
                String newMobilnr = endreMobilnr.getText().toString();

                if (validUserInfo(newName, newBirthday, newEmail, newMobilnr)) {
                    UpdateUser(newName, newBirthday, newEmail, newMobilnr);
                    setUserData();
                } else {
                    toastMessage("You must put something in the text field");
                }
            }
        });
    }

    private void setUserData() {
        endreNavn.setText(sharedPreferences.getString(User.NAME, null));
        endreBursdag.setText(sharedPreferences.getString(User.BIRTHDAY, null));
        endreEmail.setText(sharedPreferences.getString(User.EMAIL, null));
        endreMobilnr.setText(sharedPreferences.getString(User.MOBILNR, null));
    }

    public void UpdateUser(String newName, String newBirthday, String newEmail, String newMobilnr) {
        boolean insertData = database.updateUserInDatabase(sharedPreferences.getString(User.ID, null), newName, newBirthday, newEmail, newMobilnr);

        if (insertData)
            toastMessage("Data successfully inserted");
        else
            toastMessage("Something went wrong");
    }

    private boolean validUserInfo(String name, String email, String birthday, String mobilnr) {
        return name.length() != 0 && email.length() != 0 && birthday.length() != 0 && mobilnr.length() != 0;

        //Kan evt. legge in regEx på email etterhvert.
    }
}