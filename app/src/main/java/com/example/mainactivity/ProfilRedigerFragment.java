package com.example.mainactivity;

import android.content.SharedPreferences;
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
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class ProfilRedigerFragment extends Fragment {

    Database database;
    SharedPreferences sharedPreferences;

    private Button avbryt, send;
    private EditText endreNavn, endreEmail, endreMobilnr;
    private DatePicker endreBursdag;
    private String navn, mobil, email, dato, id;

    public ProfilRedigerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_rediger, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment);

        // Instansierer variabler
        avbryt = view.findViewById(R.id.EndreProfilAvbryt);
        send = view.findViewById(R.id.EndreProfilRedigerBtn);
        endreNavn = view.findViewById(R.id.endreProfilNavn);
        endreBursdag = view.findViewById(R.id.endreProfilBursdag);
        endreEmail = view.findViewById(R.id.endreProfilEmail);
        endreMobilnr = view.findViewById(R.id.profilMobilnr);
        database = new Database(getActivity());

        endreNavn.setText(getArguments().getString("NAVN"));
        endreEmail.setText(getArguments().getString("EMAIL"));
        endreMobilnr.setText(getArguments().getString("MOBILNUMMER"));

        String[] parts = getArguments().getString("FODSELSDATO").split("\\.");
        Integer dag, maaned, aar;
        dag = Integer.parseInt(parts[0]);
        maaned = Integer.parseInt(parts[1]);
        aar = Integer.parseInt(parts[2]);

        endreBursdag.updateDate(aar, maaned-1, dag);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navn = endreNavn.getText().toString();
                mobil = endreMobilnr.getText().toString();
                email = endreEmail.getText().toString();
                dato = endreBursdag.getDayOfMonth() + "." + (endreBursdag.getMonth()+1) + "." + endreBursdag.getYear();
                id = getArguments().getString("ID");
                database.updateUserInDatabase(id, navn, email, dato, mobil);
                navController.navigateUp();
            }
        });

        avbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
    }
}
