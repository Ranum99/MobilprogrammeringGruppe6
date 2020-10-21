package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {

    Database database;
    SharedPreferences sharedPreferences;
    private EditText email, password;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button login = view.findViewById(R.id.Login);
        login.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_hovedsideFragment));

        Button opprettBruker = view.findViewById(R.id.OpprettBruker);
        opprettBruker.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_signupFragment));

        password = view.findViewById(R.id.PasswordLogin);
        email = view.findViewById(R.id.EmailLogin);

    }

}