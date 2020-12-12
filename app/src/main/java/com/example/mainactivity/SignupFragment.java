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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupFragment extends Fragment {
    public SignupFragment() {
        // Required empty constructor
    }

    Database database;
    User user;
    SharedPreferences sharedPreferences;

    private EditText aName, anEmail, aPassword, aPasswordConfirm, aMobilnr;
    private DatePicker aBirthday;
    private Button registrerBruker;
    private ImageView logo;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        registrerBruker = view.findViewById(R.id.SignupRegistrerBruker);
        aName = view.findViewById(R.id.SignupNavnInput);
        anEmail = view.findViewById(R.id.SignupEmailInput);
        aBirthday = view.findViewById(R.id.BirthdayDate);
        aMobilnr = view.findViewById(R.id.SignupMobilnummerInput);
        aPassword = view.findViewById(R.id.SignupOprettPassordInput);
        aPasswordConfirm = view.findViewById(R.id.SignupGjentaPassordInput);
        database = new Database(getActivity());
        logo = view.findViewById(R.id.SignupLogo);
        logo.setImageResource(R.drawable.logo);
        aBirthday.setMaxDate(System.currentTimeMillis());

        registrerBruker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = aName.getText().toString();
                String email = anEmail.getText().toString();
                String birthday = aBirthday.getDayOfMonth() + "." + (aBirthday.getMonth()+1) + "." + aBirthday.getYear();
                String mobilnr = aMobilnr.getText().toString();
                String password = aPassword.getText().toString();
                String passwordConfirm = aPasswordConfirm.getText().toString();

                if (validUserInfo(name, email, birthday, mobilnr, password, passwordConfirm)) {
                    if (!checkIfUserallreadyIsMember(email)) {
                        Toast.makeText(getActivity(), "Mailen finnes fra før", Toast.LENGTH_SHORT).show();
                        return;
                    }
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
                        aMobilnr.setText("");

                        Bundle bundle = new Bundle();
                        bundle.putString("DATO", birthday);
                        bundle.putString("NAVN", name);
                        navController.navigate(R.id.familieFragment, bundle);
                    }

                }
            }
        });
    }

    private boolean checkIfUserallreadyIsMember(String email) {
        Cursor insertData = database.getData(Database.TABLE_USER);

        while(insertData.moveToNext()) {
            String mailen = insertData.getString(insertData.getColumnIndex(Database.COLUMN_EMAIL));
            if (mailen.equals(email))
                return false;
        }
        return true;
    }

    public boolean AddUser(String name, String email, String birthday, String mobilnr, String password) {
        boolean insertData = database.addUserToDatabase(name, email, birthday, mobilnr, password);

        if (insertData ) {
            Toast.makeText(getActivity(), "Data successfully inserted", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean validUserInfo(String name, String email, String birthday, String mobilnr, String password, String passwordConfirm) {
        Pattern mailRegEx = Pattern.compile("^[-a-z0-9~!$%^&_=+}{'?]+(.[-a-z0-9~!$%^&=+}{'?]+)*@([a-z0-9][-a-z0-9]*(.[-a-z0-9]+)*.(aero|arpa|biz|com|coop|edu|gov|info|int|mil|museum|name|net|org|pro|travel|mobi|[a-z][a-z])|([0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}.[0-9]{1,3}))(:[0-9]{1,5})?$");
        Matcher matcher = mailRegEx.matcher(email);

        if (name.length() == 0 || email.length() == 0 || password.length() == 0 || passwordConfirm.length() == 0 || birthday.length() == 0 || mobilnr.length() == 0) {
            Toast.makeText(getActivity(), "Fyll ut feltene", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!matcher.find()) {
            Toast.makeText(getActivity(), "Du må fylle inn en riktig mail", Toast.LENGTH_SHORT).show();
            return false;
        }
        return password.equals(passwordConfirm);

        //Kan evt. legge in regEx på email etterhvert.
    }
}