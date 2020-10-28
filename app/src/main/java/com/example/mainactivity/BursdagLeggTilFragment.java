package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BursdagLeggTilFragment extends Fragment {

    public BursdagLeggTilFragment() {}

    Database database;

    private Button lagre = getActivity().findViewById(R.id.NyBursdagLagre);
    private EditText FullName = getActivity().findViewById(R.id.BirthdayFullName);
    private EditText Birthday = getActivity().findViewById(R.id.BirthdayDate);
    private EditText PhoneNumber = getActivity().findViewById(R.id.BirthdayPhoneNumber);

    public static final String KEY_NAME = "edit_Name";
    public static final String KEY_PHONE = "edit_Phone";
    public static final String KEY_DATE = "edit_Date";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bursdag_legg_til, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());

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

                if (validUserInfo(name, phone, date)) {
                    AddUser(name, phone, date);
                    FullName.setText("");
                    Birthday.setText("");
                    PhoneNumber.setText("");
                } else {
                    Toast.makeText(getActivity(), "You must put something in the text field", Toast.LENGTH_SHORT).show();
                }

                //startActivity(intent);

            }
        });


    }

    public void AddUser(String name, String phone, String date) {
        boolean insertData = database.addUserToDatabaseBIRTHDAY(name, phone, date);
        if (insertData)
            Toast.makeText(getActivity(), "Data successfully inserted", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
    }
    private boolean validUserInfo(String name, String phone, String date) {
        if (name.length() == 0 || phone.length() == 0 || date.length() == 0)
            return false;
        return true;
    }
}