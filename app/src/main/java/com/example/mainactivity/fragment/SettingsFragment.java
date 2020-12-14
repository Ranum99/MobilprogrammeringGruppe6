package com.example.mainactivity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;

import com.example.mainactivity.R;
import com.example.mainactivity.model.User;

public class SettingsFragment extends Fragment {
    public SettingsFragment() {
        // Required empty public constructor
    }

    private SwitchCompat darkModeSwitch;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Lagrer staten for å sjekke om appen er i darkmode
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final boolean isDarkModeOn = sharedPreferences.getBoolean("isDarkModeOn", true);

        darkModeSwitch = view.findViewById(R.id.darkmodeSwitch);

        // Når en bruker åpner appen etter å ha skrudd på dark mode/light mode
        if (isDarkModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            //farge.setText("Skru av Dark Mode");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            //farge.setText("Skru på Dark Mode");
        }

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isDarkModeOn) {
                // Når brukeren trykker på dark mode knappen
                if (isDarkModeOn) {
                    // Hvis dark mode er på så vil det bli skrudd av
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    Log.i("SettingsFragment", "Nightmode er av");

                    // Setter isDarkModeOn boolean til false
                    editor.putBoolean("isDarkModeOn", false);
                    editor.apply();

                    // Endrer teksten på knappen
                    //farge.setText("Skru på dark mode");
                } else {
                    // Hvis dark mode er av så vil det bli skrudd på
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    Log.i("SettingsFragment", "Nightmode er på");

                    // Setter isDarkModeOn boolean til true
                    editor.putBoolean("isDarkModeOn", true);
                    editor.apply();

                    // Endrer teksten på knappen
                    //farge.setText("Skru av dark mode ");
                }
            }
        });
    }
}