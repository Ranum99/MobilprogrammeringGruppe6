package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

public class MatplanLeggTilFragment extends Fragment {

    public MatplanLeggTilFragment() {}

    private Spinner antallDager, startdag;
    private Button avbryt, opprett;

    Integer[] AntallDager = {1,2,3,4,5,6,7};
    String[] UkeDager = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matplan_legg_til, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        antallDager = view.findViewById(R.id.matplanDager);
        startdag = view.findViewById(R.id.matplanStartDag);
        avbryt = view.findViewById(R.id.matplanAvbryt);
        opprett = view.findViewById(R.id.opprettMatplan);

        avbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
        opprett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.matplanListeFragment);
            }
        });


    }
}