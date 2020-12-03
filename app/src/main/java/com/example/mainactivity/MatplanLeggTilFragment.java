package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MatplanLeggTilFragment extends Fragment {

    public MatplanLeggTilFragment() {
        // Required empty constructor
    }

    // Variabler
    private Spinner antallDager, startdag;
    private Button avbryt, opprett;
    private String valgtStartDag, valgtAntallDager, id;
    Database database;
    SharedPreferences sharedPreferences;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matplan_legg_til, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment);

        // Variabler
        database = new Database(getActivity());
        antallDager = view.findViewById(R.id.matplanDager);
        startdag = view.findViewById(R.id.matplanStartDag);
        avbryt = view.findViewById(R.id.matplanAvbryt);
        opprett = view.findViewById(R.id.opprettMatplan);

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        arrayList.add("7");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                requireActivity().getBaseContext(),
                android.R.layout.simple_spinner_item,
                arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        antallDager.setAdapter(arrayAdapter);
        antallDager.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valgtAntallDager = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Du har valgt å lage en matplan for " + valgtAntallDager + " dager", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayList<String> arrayList2 = new ArrayList<>();
        arrayList2.add("Mandag");
        arrayList2.add("Tirsdag");
        arrayList2.add("Onsdag");
        arrayList2.add("Torsdag");
        arrayList2.add("Fredag");
        arrayList2.add("Lørdag");
        arrayList2.add("Søndag");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(
                requireActivity().getBaseContext(),
                android.R.layout.simple_spinner_item,
                arrayList2);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startdag.setAdapter(arrayAdapter2);
        startdag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                valgtStartDag = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Du har valgt " + valgtStartDag + " som startdag", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        avbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
        opprett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getArguments() != null;
                id = getArguments().getString("ID");
                database.addTempDataMatplan(id, valgtAntallDager, valgtStartDag);

                navController.navigate(R.id.matplanListeFragment);
            }
        });
    }
}