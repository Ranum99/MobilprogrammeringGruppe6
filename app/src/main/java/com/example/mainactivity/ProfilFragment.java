package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ProfilFragment extends Fragment {
    public ProfilFragment() {}


    private SharedPreferences sharedPreferences;
    private TextView userID;
    private Button endreProfilBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Endre profil
        endreProfilBtn = view.findViewById(R.id.btnEndreProfil);
        endreProfilBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.profilRedigerFragment));

        sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, MODE_PRIVATE);
        userID = view.findViewById(R.id.ProfilBrukerId);
        userID.setText(userID.getText() + sharedPreferences.getString(User.ID, null));
    }
}