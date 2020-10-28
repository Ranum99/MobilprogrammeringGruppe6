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
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;

public class HovedsideFragment extends Fragment {
    Button LoggUt;

    public HovedsideFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hovedside, container, false);
    }

    public <T> void goToNewSiteListener(int idBtn, final Class<T> classToUse) {
        requireActivity().findViewById(idBtn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent activity2Intent = new Intent(requireActivity().getApplicationContext(), classToUse);
                startActivity(activity2Intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        goToNewSiteListener(R.id.goToHandleliste, HandlelisteTemp.class);
        goToNewSiteListener(R.id.goToKalender, KalenderTemp.class);
        goToNewSiteListener(R.id.goToMatplan, MatplanListe.class);
        goToNewSiteListener(R.id.goToFamiliebobla, Familiebobla.class);
        goToNewSiteListener(R.id.goToBursdager, Bursdager.class);
        goToNewSiteListener(R.id.goToOnskeliste, Onskeliste.class);



        LoggUt = requireActivity().findViewById(R.id.LoggUt);

        Intent intent = requireActivity().getIntent();
        String string = intent.getStringExtra("message");
        LoggUt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation Popup!").
                        setMessage("Are you sure that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Tømmer sharedPreferences
                                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                requireActivity().finish();

                                Intent intent1 = new Intent(requireActivity().getApplicationContext(), LoginFragment.class);
                                startActivity(intent1);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });

        TabLayout tabLayout = requireActivity().findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                goToNewSiteListener(R.id.goToProfile, ProfilFragment.class);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });
    }
}