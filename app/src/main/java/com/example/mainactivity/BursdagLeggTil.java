package com.example.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class BursdagLeggTil extends Activity {

    private Button lagre = findViewById(R.id.BirthdayLagre);
    private EditText FullName = findViewById(R.id.BirthdayFullName);
    private EditText Birthday = findViewById(R.id.BirthdayDate);
    private EditText PhoneNumber = findViewById(R.id.BirthdayPhoneNumber);

    public static final String KEY_NAME = "edit_Name";
    public static final String KEY_PHONE = "edit_Phone";
    public static final String KEY_DATE = "edit_Date";

    ArrayList<String> Name = new ArrayList<String>();
    ArrayList<String> Phone = new ArrayList<String>();
    ArrayList<String> Date = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bursdag_legg_til);

       lagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name.add(FullName.getText().toString());
                Phone.add(PhoneNumber.getText().toString());
                Date.add(Birthday.getText().toString());

                Intent intent = new Intent(v.getContext(), Bursdager.class);
                intent.putExtra(KEY_NAME, Name);
                intent.putExtra(KEY_PHONE, Phone);
                intent.putExtra(KEY_DATE, Date);

                startActivity(intent);

            }
        });

        endActivityAndGoBack(R.id.tilbakeBtn);
        endActivityAndGoBack(R.id.avbrytBtn);

    }
}