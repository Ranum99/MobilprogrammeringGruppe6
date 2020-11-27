package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HandlelisteLeggTil extends Fragment {

    public HandlelisteLeggTil() {
        // Required empty constructor
    }

    // Variabler
    Database database;
    SharedPreferences sharedPreferences;
    EditText HandlelisteUkeNr, item;
    ListView handleliste1;
    Button LeggTilIHandleliste, lagreHandleliste;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_handleliste_legg_til, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        HandlelisteUkeNr = view.findViewById(R.id.HandlelisteUkeNr);
        item = view.findViewById(R.id.item);
        handleliste1 = view.findViewById(R.id.handleliste);
        LeggTilIHandleliste = view.findViewById(R.id.LeggTilIHandleliste);
        lagreHandleliste = view.findViewById(R.id.lagreHandleliste);

        LeggTilIHandleliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String leggTilGjenstand = item.getText().toString();
                String leggTilUkeNr = HandlelisteUkeNr.getText().toString();

            }
        });
    }
}