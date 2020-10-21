package com.example.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

    Database database;
    SharedPreferences sharedPreferences;

    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        goToNewSiteListener(R.id.BirtdayLagre, Signup.class);
        goToNewSiteListener(R.id.btnLoggInn, MainActivity.class);
        endActivityAndGoBack(R.id.tilbakeBtn);

        email = (EditText) findViewById(R.id.LoginEmail);
        password = (EditText) findViewById(R.id.loginPassord);
        Button btnLogin = (Button) findViewById(R.id.btnLoggInn);
        database = new Database(this);

        email.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase().replace(" ", "");
                    }
                }
        });

        sharedPreferences = getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Dette skal med, men for å slippe å logge inn
                String emailen = email.getText().toString();
                String passordet = password.getText().toString();

                if (emailen.length() != 0 && passordet.length() != 0) {
                    if (LoginUser(emailen, passordet)) {


                        email.setText("");
                        password.setText("");
                    }
                } else
                    toastMessage("Du må fylle ut feltene");
                /*Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
                finish();*/
            }
        });
    }


    public boolean LoginUser(String email, String password) {
        Cursor data = database.getData();
        while(data.moveToNext()) {

            if (data.getString(2).equals(email) && data.getString(5).equals(password)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(User.ID, data.getString(0));
                editor.putString(User.NAME, data.getString(1));
                editor.putString(User.EMAIL, data.getString(2));
                editor.putString(User.BIRTHDAY, data.getString(3));
                editor.putString(User.MOBILNR, data.getString(4));
                editor.apply();

                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
                finish();
                return true;
            }
        }
        toastMessage("Not valid user");
        return false;
    }

}