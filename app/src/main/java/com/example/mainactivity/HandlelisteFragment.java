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
import android.widget.ListView;
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
    private Integer familieID;
    private String bruker;

    // ArrayList for å lagre dataen fra databasen
    private ArrayList<HandlelisteModel> handleliste = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_handleliste, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        familieID = Integer.valueOf(sharedPreferences.getString(User.FAMILIE, null));
        bruker = sharedPreferences.getString(User.NAME, null);
        NyHandleliste = view.findViewById(R.id.NyHandleliste);
        empty = view.findViewById(R.id.emptyHandleliste);
        handlelisteRecyclerView = requireView().findViewById(R.id.HandlelisteRecyclerview);

        // Metoder
        setInfo();
        setUpRecyclerView();


        if (handleliste.isEmpty()) { empty.setVisibility(View.VISIBLE); }
        else { empty.setVisibility(View.GONE); }

        // Tar deg videre til nytt fragment
        NyHandleliste.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.handlelisteLeggTilFragment));

    }

    // Metoder for å fylle Arraylisten med data fra databasen
    private void setInfo() {
        // Henter data fra databasetabell Handleliste
        Cursor data = database.getData(Database.TABLE_HANDLELISTE);

        ArrayList<HandlelisteModel> handlelister = new ArrayList<>();

        while(data.moveToNext()) {


            String id = data.getString(data.getColumnIndex(Database.COLUMN_ID));
            String tittel = data.getString(data.getColumnIndex(Database.COLUMN_HANDLELISTE_TITTEL));
            HandlelisteModel liste = new HandlelisteModel(tittel, id, familieID, bruker);
            handlelister.add(liste);
        }

        this.handleliste = handlelister;

    }

    private void setUpRecyclerView() {

        handlelisteRecyclerView.setAdapter(new HandlelisteAdapter(getContext(), handleliste, familieID));
        handlelisteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}