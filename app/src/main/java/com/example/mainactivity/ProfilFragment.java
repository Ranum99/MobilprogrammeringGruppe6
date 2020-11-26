package com.example.mainactivity;

import android.annotation.SuppressLint;
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
import androidx.navigation.NavController;
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
    public ProfilFragment() {
        // Required
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, MODE_PRIVATE);
        TextView userID = view.findViewById(R.id.ProfilBrukerId);
        userID.setText(userID.getText() + sharedPreferences.getString(User.ID, null));

        // Endre profil
        Button endreProfilBtn = view.findViewById(R.id.btnEndreProfil);
        endreProfilBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.profilRedigerFragment));

        //Logg ut
        Button loggUt = view.findViewById(R.id.LoggutBtn);
        loggUt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Nei, ikke gå!!!").
                        setMessage("Er du sikker på at du vil logge ut?");
                builder.setPositiveButton("Ja, jeg vil ut herifra!",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Tømmer sharedPreferences
                                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                navController.navigate(R.id.loginFragment);
                            }
                        });
                builder.setNegativeButton("Nei, ta meg tilbake!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });

        sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, MODE_PRIVATE);
        userID = view.findViewById(R.id.ProfilBrukerId);
        userID.setText(userID.getText() + sharedPreferences.getString(User.ID, null));

        TextView mobilnr = view.findViewById(R.id.ProfilMobilNummer);
        mobilnr.setText(mobilnr.getText() + sharedPreferences.getString(String.valueOf(User.MOBILNR), null));

        TextView fodselsdato = view.findViewById(R.id.ProfilFodselsdato);
        fodselsdato.setText(fodselsdato.getText() + sharedPreferences.getString(String.valueOf(User.BIRTHDAY), null));

    }
}