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
    public BursdagFragment() {}

    private Button NyBursdag;
    Database database;
    SharedPreferences sharedPreferences;
    private ArrayList<String> navn, mobil, dato;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bursdag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        setInfo();
        setUpRecyclerView();

        NyBursdag = view.findViewById(R.id.BursdagNyBursdag);
        NyBursdag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.bursdagLeggTilFragment);
            }
        });

    }

    private void setInfo() {
        Cursor data = database.getData(Database.TABLE_BIRTHDAY);

        ArrayList<String> navn = new ArrayList<>();
        ArrayList<String> mobil = new ArrayList<>();
        ArrayList<String> dato = new ArrayList<>();

        while(data.moveToNext()) {

            navn.add(Database.COLUMN_NAME_BIRTHDAY);
            dato.add(Database.COLUMN_BIRTHDAY_DATE);
            mobil.add(Database.COLUMN_PHONENUMBER_BIRTHDAY);
        }
    }

    private void setUpRecyclerView() {
        RecyclerView bursdagRecyclerView = getView().findViewById(R.id.BursdagRecyclerview);
        bursdagRecyclerView.setAdapter(new BirthdayAdapter(getContext(), BirthdayModel.getData(navn, mobil, dato)));

        bursdagRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}