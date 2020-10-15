package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class BursdagLeggTil extends Activity {

    public static final String KEY_NAME = "edit_Name";
    public static final String KEY_PHONE = "edit_Phone";
    public static final String KEY_DATE = "edit_Date";


    private Button lagre;
    private EditText FullName;
    private EditText Birthday;
    private EditText PhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bursdag_legg_til);

        FullName = findViewById(R.id.BirthdayFullName);
        Birthday = findViewById(R.id.BirthdayDate);
        PhoneNumber = findViewById(R.id.BirthdayPhoneNumber);
        lagre = findViewById(R.id.BirtdayLagre);
        lagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Bursdager.class);

                String PersonName = FullName.getText().toString();
                String Phone = PhoneNumber.getText().toString();
                String BirthdayDate = Birthday.getText().toString();

                intent.putExtra(KEY_NAME, PersonName);
                intent.putExtra(KEY_PHONE, Phone);
                intent.putExtra(KEY_DATE, BirthdayDate);
                startActivity(intent);
            }
        });


        endActivityAndGoBack(R.id.tilbakeBtn);
        endActivityAndGoBack(R.id.avbrytBtn);



    }
}