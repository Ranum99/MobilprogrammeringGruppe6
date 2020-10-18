package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileChange extends Activity{
    Database database;
    SharedPreferences sharedPreferences;

    private TextView userID;
    private EditText editNavn, editBirthday, editEmail;
    private Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_change);

        endActivityAndGoBack(R.id.tilbakeBtn);

        sharedPreferences = getSharedPreferences(User.SESSION, MODE_PRIVATE);

        userID = (TextView) findViewById(R.id.userID);
        userID.setText(userID.getText() + sharedPreferences.getString(User.ID, null));

        editNavn = (EditText) findViewById(R.id.editNavn);

        editBirthday = (EditText) findViewById(R.id.editBirthday);

        editEmail = (EditText) findViewById(R.id.editEmail);

        setUserData();

        saveChanges = (Button) findViewById(R.id.btnLagreEndringerProfil);
        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = editNavn.getText().toString();
                String newBirthday = editNavn.getText().toString();
                String newEmail = editNavn.getText().toString();

                if (validUserInfo(newName, newBirthday, newEmail)) {
                    UpdateUser(newName, newBirthday, newEmail);
                    setUserData();
                } else {
                    toastMessage("You must put something in the text field");
                }
            }
        });
    }

    private void setUserData() {
        editNavn.setText(sharedPreferences.getString(User.NAME, null));
        editBirthday.setText(sharedPreferences.getString(User.BIRTHDAY, null));
        editEmail.setText(sharedPreferences.getString(User.EMAIL, null));
    }

    public void UpdateUser(String newName, String newBirthday, String newEmail) {
        boolean insertData = database.updateUserInDatabase(sharedPreferences.getString(User.ID, null), newName, newBirthday, newEmail);

        if (insertData)
            toastMessage("Data successfully inserted");
        else
            toastMessage("Something went wrong");
    }

    private boolean validUserInfo(String name, String email, String birthday) {
        if (name.length() == 0 || email.length() == 0 || birthday.length() == 0)
            return false;

        //Kan evt. legge in regEx på email etterhvert.

        return true;
    }
}