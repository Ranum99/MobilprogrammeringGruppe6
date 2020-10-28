package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

public class GruppeinformasjonFragment extends Fragment {
    public GruppeinformasjonFragment() {}

    private Button EndreFamilieNavn, LeggTilMedlem, KastUtMedlem;
    private ImageButton tilbake;
    private EditText FamilieNavnInput, LeggTilMedlemInput;
    private ListView ListeOverMedlemmer;
    private Spinner MedlemDropdown;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gruppeinformasjon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EndreFamilieNavn = view.findViewById(R.id.EndreFamilieNavnBtn);
        LeggTilMedlem = view.findViewById(R.id.leggTilMedlemBtn);
        KastUtMedlem = view.findViewById(R.id.KastUtMedlemBtn);
        tilbake = view.findViewById(R.id.GruppeinfoTilbake);
        FamilieNavnInput = view.findViewById(R.id.EndreFamilieNavnInput);
        LeggTilMedlemInput = view.findViewById(R.id.LeggTilMedlemInput);
        ListeOverMedlemmer = view.findViewById(R.id.listeOverMedlemmer);
        MedlemDropdown = view.findViewById(R.id.kastUtMedlemDropdown);



    }
}