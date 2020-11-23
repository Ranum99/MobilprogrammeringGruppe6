package com.example.mainactivity;

import android.content.SharedPreferences;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MatplanFragment extends Fragment {

    public MatplanFragment() {
        // Required empty constructor

    }

    // Elementer i layouten
    private FloatingActionButton nyMatplan;

    // Variabler for å hente fra database
    private Database database;
    private SharedPreferences sharedPreferences;

    // ArrayList for å lagre dataen fra databasen
    private ArrayList<MatplanModel> matplan = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matplan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        nyMatplan = view.findViewById(R.id.NyMatplan);
        setUpRecyclerView();

        nyMatplan.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_matplanFragment_to_matplanLeggTilFragment));
    }

    private void setUpRecyclerView() {
        RecyclerView matplanRecyclerview = getView().findViewById(R.id.matplanRecyclerview);
        matplanRecyclerview.setAdapter(new MatplanAdapter(getContext(), matplan));

        matplanRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}