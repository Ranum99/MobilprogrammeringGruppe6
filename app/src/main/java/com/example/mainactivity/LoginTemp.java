package com.example.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    SharedPreferences sharedPreferences;

    private Button btnLogin, seeList;
    private EditText email, password;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_temp);

        goToNewSiteListener(R.id.signUpBtn, SignupTemp.class);
        goToNewSiteListener(R.id.btnLogin, MainActivity.class);


        email = (EditText) findViewById(R.id.etEmailLogin);
        password = (EditText) findViewById(R.id.etPassordLogin);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        database = new Database(this);
        listView = (ListView) findViewById(R.id.listView);
        seeList = (Button) findViewById(R.id.seeList);

        sharedPreferences = getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Dette skal med, men for å slippe å logge inn
                // TODO: Ta vekk ListView og knapp for å vise i login
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
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(User.ID, data.getString(0));
                editor.putString(User.NAME, data.getString(1));
                editor.putString(User.EMAIL, data.getString(2));
                editor.putString(User.BIRTHDAY, data.getString(4));
                editor.commit();


                Intent activity2Intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(activity2Intent);
                finish();
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