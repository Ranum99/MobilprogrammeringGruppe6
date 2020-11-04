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
import com.google.android.material.tabs.TabLayout;

public class HovedsideFragment extends Fragment {
    public HovedsideFragment() {}
    public static final String ARG_OBJECT = "object";


    private Button LoggUt;
    private TextView FamilyName, FamilyId;
    private CardView Handleliste, Matplan, Bursdager, Kalender, Familiebobla, Onskeliste;
    private TabItem profil, gruppeinfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_hovedside, container, false);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LoggUt = view.findViewById(R.id.LoggUt);
        FamilyName = view.findViewById(R.id.FamilyName);
        FamilyId = view.findViewById(R.id.FamilyId);

        profil = view.findViewById(R.id.goToProfile);
        //profil.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_hovedsideFragment_to_profilFragment));
        gruppeinfo = view.findViewById(R.id.goToGruppeinformasjon);
        //gruppeinfo.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_hovedsideFragment_to_gruppeinformasjonFragment));

        Handleliste = view.findViewById(R.id.goToHandleliste);
        Handleliste.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_hovedsideFragment_to_handlelisteFragment));
        Matplan = view.findViewById(R.id.goToMatplan);
        Matplan.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_hovedsideFragment_to_matplanFragment));
        Bursdager = view.findViewById(R.id.goToBursdager);
        Bursdager.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_hovedsideFragment_to_bursdagFragment));
        Kalender = view.findViewById(R.id.goToKalender);
        Kalender.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_hovedsideFragment_to_kalenderFragment));
        Familiebobla = view.findViewById(R.id.goToFamiliebobla);
        Familiebobla.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_hovedsideFragment_to_familieboblaFragment));
        Onskeliste = view.findViewById(R.id.goToOnskeliste);
        Onskeliste.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_hovedsideFragment_to_onskelisteFragment));

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


                                //Intent intent1 = new Intent(requireActivity().getApplicationContext(), LoginFragment.class);
                                //startActivity(intent1);
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

    }
}