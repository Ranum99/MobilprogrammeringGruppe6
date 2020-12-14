package com.example.mainactivity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mainactivity.Database;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;
import com.google.android.material.textfield.TextInputLayout;

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
                        Log.e("SignupFragment", "Bruker skrev en mail som finnes fra før");
                        return;
                    }
                    if (AddUser(name, email, birthday , mobilnr, password)) {
                        // Setter session
                        Cursor data = database.getIdOfUserData(email);

                        String idTilBruker = "1";

                        while(data.moveToNext()) {
                            Log.i("SignupFragment", "ID of user: " + data.getString(data.getColumnIndex(Database.COLUMN_ID)));
                            idTilBruker = data.getString(data.getColumnIndex(Database.COLUMN_ID));
                        }

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
            Log.i("SignupFragment", "Data successfully inserted");
            return true;
        }
        else {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            Log.e("SignupFragment", "Something went wrong");
            return false;
        }
    }

    // sjekker input
    private boolean validUserInfo(String name, String email, String birthday, String mobilnr, String password, String passwordConfirm) {
        Pattern mailRegEx = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
        Matcher matcher = mailRegEx.matcher(email);

        // Alle felter er fylt ut
        if (name.length() == 0 || email.length() == 0 || password.length() == 0 || passwordConfirm.length() == 0 || birthday.length() == 0 || mobilnr.length() == 0) {
            Toast.makeText(getActivity(), "Fyll ut feltene", Toast.LENGTH_SHORT).show();
            Log.e("SignupFragment", "Brukeren fylte ikke ut feltene");
            return false;
        }
        // sjekker mail oppmot regex
        if (!matcher.find()) {
            Toast.makeText(getActivity(), "Du må fylle inn en riktig mail", Toast.LENGTH_SHORT).show();
            Log.e("SignupFragment", "Brukeren fylte ikke inn riktig email");
            return false;
        }
        // passord og passordConfirm er like
        if (!password.equals(passwordConfirm)) {
            Toast.makeText(getActivity(), "Passordene er ikke like", Toast.LENGTH_SHORT).show();
            Log.e("SignupFragment", "Brukeren skrev to ulike passord");
            return false;
        }
        // passord er minst 8 tegn langt
        if (!stringAgainstRegex(".{8,}", passwordConfirm)) {
            Toast.makeText(getActivity(), "Passordet må minst være 8 tegn", Toast.LENGTH_SHORT).show();
            Log.e("SignupFragment", "Brukeren skrev for kort passord");
            return false;
        }
        // passord inneholder minst 1 bokstav (liten/stor)
        if (!stringAgainstRegex("(?=.*[a-å])", passwordConfirm) || !stringAgainstRegex("(?=.*[A-Å])", passwordConfirm)) {
            Toast.makeText(getActivity(), "Passordet må inneholde minst en bokstav", Toast.LENGTH_SHORT).show();
            Log.e("SignupFragment", "Brukeren skrev ikke bokstav i passord");
            return false;
        }
        // passord kan ikke inneholde whitespace
        if (!stringAgainstRegex("(?=\\S+$)", passwordConfirm)) {
            Toast.makeText(getActivity(), "Passordet kan ikke inneholde mellomrom", Toast.LENGTH_SHORT).show();
            Log.e("SignupFragment", "Brukeren skrev mellomrom i passord");
            return false;
        }
        // passord inneholder minst 1 tall
        if (!stringAgainstRegex("(?=.*[0-9])", passwordConfirm)) {
            Toast.makeText(getActivity(), "Passordet må inneholde minst ett tall", Toast.LENGTH_SHORT).show();
            Log.e("SignupFragment", "Brukeren skrev ikke tall i passord");
            return false;
        }
        return true;
    }

    private boolean stringAgainstRegex(String regex, String string) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(string);

        return m.find();
    }
}