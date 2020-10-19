package com.example.mainactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Signup extends Activity {

    Database database;
    User user;

    private Button btnSend;
    private EditText aName, anEmail, aPassword, aPasswordConfirm, aBirthday, aMobilnr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        endActivityAndGoBack(R.id.tilbakeBtn);


        database = new Database(this);

        btnSend = (Button) findViewById(R.id.btnSignup);

        aName = (EditText) findViewById(R.id.opprettBrukerNavn);
        anEmail = (EditText) findViewById(R.id.opprettBrukerEmail);
        aMobilnr = (EditText) findViewById(R.id.opprettBrukerMobilnr);
        aPassword = (EditText) findViewById(R.id.opprettPassord);
        aPasswordConfirm = (EditText) findViewById(R.id.opprettPassord2);
        aBirthday = (EditText) findViewById(R.id.OpprettBrukerBursdag);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = aName.getText().toString();
                String email = anEmail.getText().toString();
                String birthday = aBirthday.getText().toString();
                String mobilnr = String.valueOf(aMobilnr);
                String password = aPassword.getText().toString();
                String passwordConfirm = aPasswordConfirm.getText().toString();

                if (validUserInfo(name, email, birthday, mobilnr, password, passwordConfirm)) {
                    AddUser(name, email, birthday , mobilnr, password);
                    aName.setText("");
                    anEmail.setText("");
                    aPassword.setText("");
                    aPasswordConfirm.setText("");
                    aBirthday.setText("");
                } else {
                    toastMessage("You must put something in the text field");
                }
            }
        });
    }

    public void AddUser(String name, String email, String birthday, String mobilnr, String password) {
        boolean insertData = database.addUserToDatabase(name, email, birthday, mobilnr, password);

        if (insertData)
            toastMessage("Data successfully inserted");
        else
            toastMessage("Something went wrong");
    }

    private boolean validUserInfo(String name, String email, String birthday, String mobilnr, String password, String passwordConfirm) {
        if (name.length() == 0 || email.length() == 0 || password.length() == 0 || passwordConfirm.length() == 0 || birthday.length() == 0 || mobilnr.length() == 0)
            return false;
        return password.equals(passwordConfirm);

        //Kan evt. legge in regEx på email etterhvert.
    }
}