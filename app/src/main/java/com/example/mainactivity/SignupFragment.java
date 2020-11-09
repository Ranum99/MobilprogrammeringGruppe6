package com.example.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.ImageButton;
import android.widget.Toast;

public class SignupFragment extends Fragment {
    public SignupFragment() {}

    Database database;
    User user;
    SharedPreferences sharedPreferences;

    private EditText aName, anEmail, aPassword, aPasswordConfirm, aBirthday, aMobilnr;
    private Button registrerBruker;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        registrerBruker = view.findViewById(R.id.SignupRegistrerBruker);
        aName = view.findViewById(R.id.SignupNavnInput);
        anEmail = view.findViewById(R.id.SignupEmailInput);
        aMobilnr = view.findViewById(R.id.SignupMobilnummerInput);
        aPassword = view.findViewById(R.id.SignupOprettPassordInput);
        aPasswordConfirm = view.findViewById(R.id.SignupGjentaPassordInput);
        aBirthday = view.findViewById(R.id.SignupFodselsdatoInput);

        database = new Database(getActivity());

        registrerBruker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = aName.getText().toString();
                String email = anEmail.getText().toString();
                String birthday = aBirthday.getText().toString();
                String mobilnr = aMobilnr.getText().toString();
                String password = aPassword.getText().toString();
                String passwordConfirm = aPasswordConfirm.getText().toString();

                if (validUserInfo(name, email, birthday, mobilnr, password, passwordConfirm)) {
                    if (AddUser(name, email, birthday , mobilnr, password)) {
                        // Setter session
                        Cursor data = database.getIdOfUserData(email);



                        String idTilBruker = "1";

                        while(data.moveToNext()) {
                            System.out.println("ID of user: " + data.getString(data.getColumnIndex(Database.COLUMN_ID)));
                            idTilBruker = data.getString(data.getColumnIndex(Database.COLUMN_ID));
                        }

                        System.out.println("ID: " + idTilBruker);
                        System.out.println("NAME: " + name);
                        System.out.println("EMAIL: " + email);
                        System.out.println("BIRTHDAY: " + birthday);
                        System.out.println("MOBILNR: " + mobilnr);
                        System.out.println("PASSWORD: " + password);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(User.ID, idTilBruker);
                        editor.putString(User.NAME, name);
                        editor.putString(User.EMAIL, email);
                        editor.putString(User.BIRTHDAY, birthday);
                        editor.putString(User.MOBILNR, mobilnr);
                        editor.apply();

                        aName.setText("");
                        anEmail.setText("");
                        aPassword.setText("");
                        aPasswordConfirm.setText("");
                        aBirthday.setText("");
                        aMobilnr.setText("");

                        navController.navigate(R.id.familieFragment);
                    }

                } else {
                    Toast.makeText(getActivity(), "Fyll ut feltene", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean AddUser(String name, String email, String birthday, String mobilnr, String password) {
        boolean insertData = database.addUserToDatabase(name, email, birthday, mobilnr, password);
        boolean insertBursdag = database.addUserToDatabaseBIRTHDAY(name, mobilnr, birthday);

        if (insertData && insertBursdag) {
            Toast.makeText(getActivity(), "Data successfully inserted", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validUserInfo(String name, String email, String birthday, String mobilnr, String password, String passwordConfirm) {
        if (name.length() == 0 || email.length() == 0 || password.length() == 0 || passwordConfirm.length() == 0 || birthday.length() == 0 || mobilnr.length() == 0)
            return false;
        return password.equals(passwordConfirm);

        //Kan evt. legge in regEx på email etterhvert.
    }
}