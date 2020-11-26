package com.example.mainactivity;

import android.content.Context;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.ArrayList;
import java.util.Objects;

public class HandlelisteFragment extends Fragment {

    public HandlelisteFragment() {
        // Required empty constructor
    }

    //Elementer i layouten
    private FloatingActionButton NyHandleliste;
    private TextView empty;
    private RecyclerView handlelisteRecyclerView;

    // Variabler for å hente fra database
    private Database database;
    private SharedPreferences sharedPreferences;

    // ArrayList for å lagre dataen fra databasen
    private ArrayList<HandlelisteModel> handleliste = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_handleliste, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        NyHandleliste = view.findViewById(R.id.NyHandleliste);
        empty = view.findViewById(R.id.emptyHandleliste);
        handlelisteRecyclerView = getView().findViewById(R.id.HandlelisteRecyclerview);

        // Metoder
        setUpRecyclerView();
        setInfo();
        if (handleliste.isEmpty()) { empty.setVisibility(View.VISIBLE); }
        else { empty.setVisibility(View.GONE); }

        // Tar deg videre til nytt fragment
        NyHandleliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.handlelisteListeFragment);
            }
        });
    }

    private void setInfo() {
        Cursor data = database.getData(Database.TABLE_HANDLELISTE);

        while(data.moveToNext()) {
            String overskriften = data.getString(data.getColumnIndex(Database.COLUMN_OVERSKRIFT_HANDLELISTE));
            String varene = data.getString(data.getColumnIndex(Database.COLUMN_VARER));

            overskriftHandleliste.add(overskriften);
            varer.add(varene);
        }
    }

    // Metoder for å fylle Arraylisten med data fra databasen
    private void setInfo() {
        Cursor data = database.getData(Database.TABLE_HANDLELISTE);

        ArrayList<HandlelisteModel> alleLister = new ArrayList<>();

        while(data.moveToNext()) {

            String ukenr = data.getString(data.getColumnIndex(Database.COLUMN_HANDLELISTE_UKENR));
            String id = data.getString(data.getColumnIndex(Database.COLUMN_ID));

            HandlelisteModel liste = new HandlelisteModel(ukenr, id);
            alleLister.add(liste);
        }

        this.handleliste = alleLister;

    }

    private void setUpRecyclerView() {

        handlelisteRecyclerView.setAdapter(new HandlelisteAdapter(getContext(), handleliste));
        handlelisteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}