package com.example.mainactivity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainactivity.Database;
import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;

public class FamilieBliMedlemFragment extends Fragment {

    private TextView familyID, password;
    private Button bliMedlemBtn;

    private int familyId = 0;
    private String navn, dato;
    int autoSave;

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
        final NavController navcontroller = Navigation.findNavController(view);
        database = new Database(getActivity());

        // User.SESSION is a unique variable to identify the instance of this shared preference
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        int j = sharedPreferences.getInt("key",0);

        // Default is 0 so autologin is disabled
        if(j > 0) {
            Intent activity = new Intent(getContext(), MainActivity.class);
            startActivity(activity);
        }

        familyID = view.findViewById(R.id.familie_bli_medlem_familieID);
        password = view.findViewById(R.id.familie_bli_medlem_passord);
        bliMedlemBtn = view.findViewById(R.id.familie_bli_medlem_BliMedlemBtn);

        navn = getArguments().getString("NAVN");
        dato = getArguments().getString("DATO");

        bliMedlemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sjekkOmFamilieEksisterer()) {
                    int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

                    database.updateUserFamily(meID, familyId);
                    database.addUserToDatabaseBIRTHDAY(navn, dato, String.valueOf(familyId), String.valueOf(meID), null);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(User.FAMILIE, String.valueOf(familyId));

                    // Once the user clicks login, it will add 1 to sharedPreference which will allow autologin in OnViewCreated
                    autoSave = 1;
                    editor.putInt("key", autoSave);
                    editor.apply();

                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(password.getWindowToken(), 0);

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Familie id eller passord er feil", Toast.LENGTH_SHORT).show();
                    Log.e("FamilieBliMedlemFragmen", "Familie id eller passord er feil");
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
        Log.e("FamilieBliMedlemFragmen", "Familie eksisterer:" + familieEksisterer);
        return familieEksisterer;
    }
}