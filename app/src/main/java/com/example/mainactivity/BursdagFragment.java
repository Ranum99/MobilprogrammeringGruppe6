package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class BursdagFragment extends Fragment {
    public BursdagFragment() {
        // Required empty constructor
    }

    //Elementer i layouten
    private FloatingActionButton NyBursdag;
    private CardView card;
    private ImageButton delete;

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

        ArrayList<BirthdayModel> alleBursdager = new ArrayList<>();

        while(data.moveToNext()) {
            String navnet = data.getString(data.getColumnIndex(Database.COLUMN_NAME_BIRTHDAY));
            String datoen = data.getString(data.getColumnIndex(Database.COLUMN_BIRTHDAY_DATE));
            String id = data.getString(data.getColumnIndex(Database.COLUMN_ID));

            BirthdayModel bursdag = new BirthdayModel(navnet, datoen, id);
            alleBursdager.add(bursdag);
        }

        this.bursdager = alleBursdager;
    }

    // Metode for å sette opp recyclerviewet med cardview for hver rad i databasen
    private void setUpRecyclerView() {
        RecyclerView bursdagRecyclerView = getView().findViewById(R.id.BursdagRecyclerview);
        bursdagRecyclerView.setAdapter(new BirthdayAdapter(getContext(), bursdager));
        bursdagRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}