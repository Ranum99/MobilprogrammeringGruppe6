package com.example.mainactivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
        // Required empty constructor
    }
    private Database database;
    private SharedPreferences sharedPreferences;
    private TextView userID, mobilnr, fodselsdato, email, navn;
    private String id, mobil, dato, aemail,anavn;
    private Button endreProfilBtn,loggUt;

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

        sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, MODE_PRIVATE);
        database = new Database(getActivity());
        Cursor data = database.getData(Database.TABLE_USER, Integer.parseInt(sharedPreferences.getString(User.ID, null)));

        userID = view.findViewById(R.id.ProfilBrukerId);
        mobilnr = view.findViewById(R.id.ProfilMobilNummer);
        fodselsdato = view.findViewById(R.id.ProfilFodselsdato);
        email = view.findViewById(R.id.email);
        navn = view.findViewById(R.id.navn);


        while (data.moveToNext()) {
            userID.setText("Bruker id: " + data.getString(data.getColumnIndex(Database.COLUMN_ID)));
            mobilnr.setText("Mobilnummer: " + data.getString(data.getColumnIndex(Database.COLUMN_MOBILNR)));
            fodselsdato.setText("Fødselsdato: " + data.getString(data.getColumnIndex(Database.COLUMN_BIRTHDAY)));
            email.setText("Email: " + data.getString(data.getColumnIndex(Database.COLUMN_EMAIL)));
            navn.setText(data.getString(data.getColumnIndex(Database.COLUMN_NAME)));

            id = data.getString(data.getColumnIndex(Database.COLUMN_ID));
            mobil = data.getString(data.getColumnIndex(Database.COLUMN_MOBILNR));
            dato = data.getString(data.getColumnIndex(Database.COLUMN_BIRTHDAY));
            aemail = data.getString(data.getColumnIndex(Database.COLUMN_EMAIL));
            anavn = data.getString(data.getColumnIndex(Database.COLUMN_NAME));
        }


        System.out.println(id + ", " + mobil + ", " + dato + ", " + aemail + ", " + anavn);

        // Endre profil
        endreProfilBtn = view.findViewById(R.id.btnEndreProfil);
        endreProfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putString("ID", id);
                bundle.putString("MOBILNUMMER", mobil);
                bundle.putString("FODSELSDATO", dato);
                bundle.putString("EMAIL", aemail);
                bundle.putString("NAVN", anavn);

                navController.navigate(R.id.profilRedigerFragment, bundle);


            }
        });

        //Logg ut
        loggUt = view.findViewById(R.id.LoggutBtn);
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
                                sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
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


    }
}
