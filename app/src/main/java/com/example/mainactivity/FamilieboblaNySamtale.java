package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FamilieboblaNySamtale extends Activity {
    SharedPreferences sharedPreferences;
    Database database;
    Familiebobla familiebobla;

    private Spinner spinner;
    private User selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_familiebobla_ny_samtale);

        endActivityAndGoBack(R.id.tilbakeBtn);

        database = new Database(this);
        spinner = (Spinner) findViewById(R.id.users);
        Button addSamtale = (Button) findViewById(R.id.lagSamtale);

        sharedPreferences = getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        addUsersToDropdown();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = (User) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        addSamtale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNewSamtale();
            }
        });
    }

    private void makeNewSamtale() {
        boolean insertData = familiebobla.makeNewMessageChannelWith(selectedUser);
    }

    private void addUsersToDropdown() {
        Cursor data = database.getData();
        System.out.println("ME: " + sharedPreferences.getString(User.NAME, null));
        ArrayList<User> arrayList = new ArrayList<>();

        while(data.moveToNext()) {
            if (!data.getString(1).equals(sharedPreferences.getString(User.NAME, null))) {
                int id = data.getInt(0);
                String name = data.getString(1);
                arrayList.add( new User(id,name));
            }
        }
        if (arrayList.size() > 0) {
            ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}