package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OnskelisteFragment extends Fragment {

    public OnskelisteFragment() {
        // Required empty constructor
    }

    private FloatingActionButton nyOnskeliste;
    private TextView empty;
    private RecyclerView OnskelisteRecyclerview;
    private Database database;
    private SharedPreferences sharedPreferences;
    // ArrayList for å lagre dataen fra databasen
    private ArrayList<OnskelisteModel> onskelister = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onskeliste, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nyOnskeliste = view.findViewById(R.id.OpprettOnskelisteBtn);
        OnskelisteRecyclerview = getView().findViewById(R.id.OnskelisterRecyclerview);
        empty = view.findViewById(R.id.emptyOnskeliste);


        setUpRecyclerView();
        if (onskelister.isEmpty()) { empty.setVisibility(View.VISIBLE); }
        else { empty.setVisibility(View.GONE); }


        nyOnskeliste.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_onskelisteFragment_to_onskelisteListeFragment));
    }

    private void setUpRecyclerView() {

        OnskelisteRecyclerview.setAdapter(new OnskelisteAdapter(getContext(),OnskelisteModel.getData()));
        OnskelisteRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}