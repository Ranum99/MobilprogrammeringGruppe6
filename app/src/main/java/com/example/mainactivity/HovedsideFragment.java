package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;

public class HovedsideFragment extends Fragment {
    public HovedsideFragment() {}

    private CardView Handleliste, Matplan, Bursdager, Kalender, Familiebobla, Onskeliste;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hovedside, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Handleliste = view.findViewById(R.id.goToHandleliste);
        Handleliste.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.handlelisteFragment));
        Matplan = view.findViewById(R.id.goToMatplan);
        Matplan.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.matplanFragment));
        Bursdager = view.findViewById(R.id.goToBursdager);
        Bursdager.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.bursdagFragment));
        Kalender = view.findViewById(R.id.goToKalender);
        Kalender.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.kalenderFragment));
        Familiebobla = view.findViewById(R.id.goToFamiliebobla);
        Familiebobla.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.familieboblaFragment));
        Onskeliste = view.findViewById(R.id.goToOnskeliste);
        Onskeliste.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.onskelisteFragment));
    }
}