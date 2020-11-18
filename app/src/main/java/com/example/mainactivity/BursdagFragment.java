package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;


public class BursdagFragment extends Fragment {
    public BursdagFragment() {
        // Required empty constructor
    }

    //Elementer i layouten
    private Button NyBursdag;

    // Variabler for å hente fra database
    private Database database;
    private SharedPreferences sharedPreferences;
    // ArrayList for å lagre dataen fra databasen
    private ArrayList<String> navn = new ArrayList<>();
    private ArrayList<String> dato = new ArrayList<>();
    private ArrayList<String> id = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bursdag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        // instansierer variablene
        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        // Metoder
        setInfo();
        setUpRecyclerView();

        // Tar deg videre til nytt fragment
        NyBursdag = view.findViewById(R.id.BursdagNyBursdag);
        NyBursdag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.bursdagLeggTilFragment);
            }
        });

    }

    // Metode for å fylle ArrayListene med data fra databasen.
    private void setInfo() {
        Cursor data = database.getData(Database.TABLE_BIRTHDAY);

        while(data.moveToNext()) {
            String navnet = data.getString(data.getColumnIndex(Database.COLUMN_NAME_BIRTHDAY));
            String iden = data.getString(data.getColumnIndex(Database.COLUMN_ID));
            String datoen = data.getString(data.getColumnIndex(Database.COLUMN_BIRTHDAY_DATE));
            System.out.println(Database.COLUMN_NAME_BIRTHDAY);
            navn.add(navnet);
            dato.add(datoen);
            id.add(iden);
        }

        this.navn = navn;
        this.dato = dato;
        this.id = id;
    }

    // Metode for å sette opp recyclerviewet med cradview for hver rad i databasen
    private void setUpRecyclerView() {
        RecyclerView bursdagRecyclerView = getView().findViewById(R.id.BursdagRecyclerview);

        if ( BirthdayModel.getData(navn, dato, id) != null && BirthdayModel.getData(navn, dato, id).size() > 0 ) {

            BirthdayModel.getData(navn, dato, id).clear();
        }

        bursdagRecyclerView.setAdapter(new BirthdayAdapter(getContext(), BirthdayModel.getData(navn, dato, id)));
        bursdagRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}