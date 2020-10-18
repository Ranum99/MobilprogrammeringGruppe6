package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BursdagLeggTil extends Activity {

    Database database;

    private Button lagre = findViewById(R.id.BirthdayLagre);
    private EditText FullName = findViewById(R.id.BirthdayFullName);
    private EditText Birthday = findViewById(R.id.BirthdayDate);
    private EditText PhoneNumber = findViewById(R.id.BirthdayPhoneNumber);


    public static final String KEY_NAME = "edit_Name";
    public static final String KEY_PHONE = "edit_Phone";
    public static final String KEY_DATE = "edit_Date";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bursdag_legg_til);
        database = new Database(this);

        lagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = FullName.getText().toString();
                String phone = PhoneNumber.getText().toString();
                String date = Birthday.getText().toString();

                /*
                Intent intent = new Intent(v.getContext(), Bursdager.class);
                intent.putExtra(KEY_NAME, name);
                intent.putExtra(KEY_PHONE, phone);
                intent.putExtra(KEY_DATE, date);
                */

                AdddUser(name, phone, date);

                //startActivity(intent);

            }
        });

        endActivityAndGoBack(R.id.tilbakeBtn);
        endActivityAndGoBack(R.id.avbrytBtn);

    }

    public void AdddUser(String name, String phone, String date) {
        boolean insertData = database.addUserToDatabaseBIRTHDAY(name, phone, date);
    }
}