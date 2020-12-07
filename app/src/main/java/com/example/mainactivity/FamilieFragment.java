package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class FamilieFragment extends Fragment {

    private Button bliMedlemIFamilie, opprettFamilie;
    private String navn, dato;

    public FamilieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_familie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final NavController navController = Navigation.findNavController(view);

        bliMedlemIFamilie = view.findViewById(R.id.bliMedlem);
        opprettFamilie = view.findViewById(R.id.OpprettFamilie);

        Bundle bundle = new Bundle();
        bundle.putString("NAVN", navn);
        bundle.putString("DATO", dato);

        bliMedlemIFamilie.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_familieFragment_to_familieBliMedlemFragment, bundle));
        opprettFamilie.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_familieFragment_to_familieOpprettFragment, bundle));
    }
}