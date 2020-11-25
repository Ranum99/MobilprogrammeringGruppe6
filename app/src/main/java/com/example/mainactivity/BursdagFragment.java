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
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class BursdagFragment extends Fragment{

    public BursdagFragment() {
        // Required empty constructor
    }

    private String familieIdBruker, familieIdTabell;

    //Elementer i layouten
    private FloatingActionButton NyBursdag;
    private TextView empty;
    private RecyclerView bursdagRecyclerView;

    // Variabler for å hente fra database
    private Database database;
    private SharedPreferences sharedPreferences;
    // ArrayList for å lagre dataen fra databasen
    private ArrayList<BirthdayModel> bursdager = new ArrayList<>();


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
        bursdagRecyclerView = getView().findViewById(R.id.BursdagRecyclerview);
        empty = getView().findViewById(R.id.emptyBirthday);


        // Metoder
        setInfo();
        setUpRecyclerView();

        if (bursdager.isEmpty()) { empty.setVisibility(View.VISIBLE); }
        else { empty.setVisibility(View.GONE); }

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

        ArrayList<BirthdayModel> alleBursdager = new ArrayList<>();

        while(data.moveToNext()) {

            String navnet = data.getString(data.getColumnIndex(Database.COLUMN_NAME_BIRTHDAY));
            String datoen = data.getString(data.getColumnIndex(Database.COLUMN_BIRTHDAY_DATE));
            String id = data.getString(data.getColumnIndex(Database.COLUMN_ID));
            String familieId = familieIdBruker;

            BirthdayModel bursdag = new BirthdayModel(navnet, datoen, id, familieId);
            alleBursdager.add(bursdag);

        }

        this.bursdager = alleBursdager;
    }

    // Metode for å sette opp recyclerviewet med cardview for hver rad i databasen
    private void setUpRecyclerView() {

        // Sortere bursdager
        Comparator<BirthdayModel> byBirthday = new Comparator<BirthdayModel>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public int compare(BirthdayModel c1, BirthdayModel c2) {
                DateTimeFormatter format = DateTimeFormatter.ofPattern("d.M.uuuu");
                return LocalDate.parse(c1.getDato(), format).compareTo(LocalDate.parse(c2.getDato(), format));
            }
        };
        Collections.sort(bursdager, byBirthday);

        bursdagRecyclerView.setAdapter(new BirthdayAdapter(getContext(), bursdager));
        bursdagRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}