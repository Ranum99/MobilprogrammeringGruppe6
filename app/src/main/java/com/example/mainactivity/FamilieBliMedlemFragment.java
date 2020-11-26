package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FamilieBliMedlemFragment extends Fragment {

    private TextView familyID, password;
    private Button bliMedlemBtn;

    private int familyId = 0;

    Database database;
    SharedPreferences sharedPreferences;

    public FamilieBliMedlemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_familie_bli_medlem, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        familyID = view.findViewById(R.id.familie_bli_medlem_familieID);
        password = view.findViewById(R.id.familie_bli_medlem_passord);
        bliMedlemBtn = view.findViewById(R.id.familie_bli_medlem_BliMedlemBtn);

        bliMedlemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sjekkOmFamilieEksisterer()) {
                    int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

                    database.updateUserFamily(meID, familyId);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(User.FAMILIE, String.valueOf(familyId));
                    editor.apply();

                    Navigation.findNavController(bliMedlemBtn).navigate(R.id.action_familieBliMedlemFragment_to_mainFragment);
                } else {
                    Toast.makeText(getActivity(), "Familie id eller passord er feil", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean sjekkOmFamilieEksisterer() {
        boolean familieEksisterer = false;

        Cursor bliMedlemAvFamilie = database.sjekkOmFamilieEksisterer(familyID.getText().toString(), password.getText().toString());

        while(bliMedlemAvFamilie.moveToNext()) {
            familyId = Integer.parseInt(bliMedlemAvFamilie.getString(0));
            familieEksisterer = true;
        }

        System.out.println("Familie eksisterer: " + familieEksisterer);

        return familieEksisterer;
    }
}