package com.example.mainactivity;

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
import android.widget.ImageButton;

public class SignupFragment extends Fragment {

    Database database;
    User user;

    private Button registrerBruker;
    private ImageButton tilbake;
    private EditText aName, anEmail, aPassword, aPasswordConfirm, aBirthday, aMobilnr;
    public SignupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        registrerBruker = view.findViewById(R.id.SignupRegistrerBruker);
        tilbake = view.findViewById(R.id.SignupTilbake);
        aName = view.findViewById(R.id.SignupNavnInput);
        anEmail = view.findViewById(R.id.SignupEmailInput);
        aMobilnr = view.findViewById(R.id.SignupMobilnummerInput);
        aPassword = view.findViewById(R.id.SignupOprettPassordInput);
        aPasswordConfirm = view.findViewById(R.id.SignupGjentaPassordInput);
        aBirthday = view.findViewById(R.id.SignupFodselsdatoInput);

        registrerBruker.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_signupFragment_to_hovedsideFragment));
        tilbake.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_signupFragment_to_loginFragment));

    }
}