package com.example.mainactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginTemp extends Activity {

    Database database;

    private Button btnLogin, seeList;
    private EditText email, password;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_temp);

        goToNewSite(R.id.signUpBtn, SignupTemp.class);
        goToNewSite(R.id.btnLogin, MainActivity.class);


        email = (EditText) findViewById(R.id.etEmailLogin);
        password = (EditText) findViewById(R.id.etPassordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        database = new Database(this);
        listView = (ListView) findViewById(R.id.listView);
        seeList = (Button) findViewById(R.id.seeList);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailen = email.getText().toString();
                String passordet = password.getText().toString();
                if (emailen.length() != 0) {
                    if (LoginUser(emailen, passordet)) {
                        email.setText("");
                        password.setText("");
                    }
                } else
                    toastMessage("You must put something in the text field");
            }
        });

        seeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateList();
            }
        });
    }

    private void populateList () {
        Log.d("LISTE", "populateList: Displaying data in the ListView");

        Cursor data = database.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()) {
            String userData = data.getString(1) + " " + data.getString(2) + " " + data.getString(3) + " " + data.getString(4);
            listData.add(userData);
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        listView.setAdapter(adapter);
    }

    public boolean LoginUser(String email, String password) {
        Cursor data = database.getData();
        while(data.moveToNext()) {
            if (data.getString(2).equals(email) && data.getString(3).equals(password)) {
                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
                return true;
            }
        }
        toastMessage("Not valid user");
        return false;
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}