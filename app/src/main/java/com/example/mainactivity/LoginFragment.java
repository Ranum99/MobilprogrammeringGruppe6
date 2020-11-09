package com.example.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Objects;

public class LoginFragment extends Fragment {
    public LoginFragment() {}

    Database database;
    SharedPreferences sharedPreferences;
    private EditText email, password;
    private Button login, opprettBruker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        opprettBruker = view.findViewById(R.id.OpprettBruker);
        opprettBruker.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_signupFragment));

        password = view.findViewById(R.id.PasswordLogin);
        email = view.findViewById(R.id.EmailLogin);
        email.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase().replace(" ", "");
                    }
                }
        });

        login = view.findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailen = email.getText().toString();
                String passordet = password.getText().toString();

                if (emailen.length() != 0 && passordet.length() != 0) {
                    if (LoginUser(emailen, passordet)) {
                        email.setText("");
                        password.setText("");
                        

                        if (sharedPreferences.getString(User.FAMILIE, null) == null)
                            Navigation.findNavController(login).navigate(R.id.action_loginFragment_to_familieFragment);
                        else
                            navController.navigate(R.id.mainFragment);
                    }
                } else
                    Toast.makeText(getActivity(), "Du må fylle ut alle feltene", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean LoginUser(String email, String password) {

        Cursor data = database.getData();

        while(data.moveToNext()) {

            if (data.getString(2).equals(email) && data.getString(5).equals(password) ) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(User.ID, data.getString(0));
                editor.putString(User.NAME, data.getString(1));
                editor.putString(User.EMAIL, data.getString(2));
                editor.putString(User.BIRTHDAY, data.getString(3));
                editor.putString(User.MOBILNR, data.getString(4));
                editor.putString(User.FAMILIE, data.getString(6));
                editor.apply();

                return true;
            }
        }
        Toast.makeText(getActivity(), "Ikke gyldig bruker", Toast.LENGTH_SHORT).show();
        return false;
    }

}