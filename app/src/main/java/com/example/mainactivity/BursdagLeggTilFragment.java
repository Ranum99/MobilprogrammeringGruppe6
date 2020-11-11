package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class BursdagLeggTilFragment extends Fragment {

    public BursdagLeggTilFragment() {}

    Database database;

    private Button lagre, avbryt;
    private EditText FullName;
    private EditText Birthday;
    private EditText PhoneNumber;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bursdag_legg_til, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        lagre = view.findViewById(R.id.NyBursdagLagre);
        avbryt = view.findViewById(R.id.NyBursdagAvbryt);
        FullName = view.findViewById(R.id.BirthdayFullName);
        Birthday = view.findViewById(R.id.BirthdayDate);
        PhoneNumber = view.findViewById(R.id.BirthdayPhoneNumber);

        lagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = FullName.getText().toString();
                String date = Birthday.getText().toString();
                String phone = PhoneNumber.getText().toString();

                if (validUserInfo(name, phone, date)) {
                    if (AddUser(name, phone, date)) {
                        FullName.setText("");
                        Birthday.setText("");
                        PhoneNumber.setText("");
                        navController.navigateUp();
                    }

                } else {
                    Toast.makeText(getActivity(), "You must put something in the text field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        avbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });


    }

    public boolean AddUser(String name, String phone, String date) {
        boolean insertData = database.addUserToDatabaseBIRTHDAY(name, phone, date);

        if (insertData) {
            Toast.makeText(getActivity(), "Data successfully inserted", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean validUserInfo(String name, String phone, String date) {
        if (name.length() == 0 || phone.length() == 0 || date.length() == 0) {
            return false;
        }
        return true;
    }
}