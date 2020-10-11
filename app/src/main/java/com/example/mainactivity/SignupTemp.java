package com.example.mainactivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupTemp extends Activity {

    Database database;

    private Button registerBtn;
    private EditText aName, anEmail, aPassword, aPasswordConfirm, aBirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_temp);

        endActivityAndGoBack(R.id.tilbakeBtn);


        database = new Database(this);

        registerBtn = (Button) findViewById(R.id.btnSignup);

        aName = (EditText) findViewById(R.id.etNavn);
        anEmail = (EditText) findViewById(R.id.etEmail);
        aPassword = (EditText) findViewById(R.id.etPassord);
        aPasswordConfirm = (EditText) findViewById(R.id.etBekreftPassord);
        aBirthday = (EditText) findViewById(R.id.etFodselsdato);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = aName.getText().toString();
                String email = anEmail.getText().toString();
                String password = aPassword.getText().toString();
                String passwordConfirm = aPasswordConfirm.getText().toString();
                String birthday = aBirthday.getText().toString();

                if (validUserInfo(name, email, password, passwordConfirm, birthday)) {
                    AddUser(name, email, password, birthday);
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

    public void AddUser(String name, String email, String password, String birthday) {
        boolean insertData = database.addUserToDatabase(name, email, password, birthday);

        if (insertData)
            toastMessage("Data successfully inserted");
        else
            toastMessage("Something went wrong");
    }

    private boolean validUserInfo(String name, String email, String password, String passwordConfirm, String birthday) {
        if (name.length() == 0 || email.length() == 0 || password.length() == 0 || passwordConfirm.length() == 0 || birthday.length() == 0)
            return false;
        if (!password.equals(passwordConfirm))
            return false;

        //Kan evt. legge in regEx på email etterhvert.

        return true;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}