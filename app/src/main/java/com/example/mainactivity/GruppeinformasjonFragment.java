package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class GruppeinformasjonFragment extends Fragment {
    public GruppeinformasjonFragment() {}

    SharedPreferences sharedPreferences;
    Database database;

    private Button endreFamilieNavn, leggTilMedlem, kastUtMedlem;
    private EditText familieNavnInput, leggTilMedlemInput;
    private ListView listeOverMedlemmer;
    private Spinner medlemDropdown;

    private User selectedUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gruppeinformasjon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        endreFamilieNavn = view.findViewById(R.id.EndreFamilieNavnBtn);
        leggTilMedlem = view.findViewById(R.id.leggTilMedlemBtn);
        kastUtMedlem = view.findViewById(R.id.KastUtMedlemBtn);
        familieNavnInput = view.findViewById(R.id.EndreFamilieNavnInput);
        leggTilMedlemInput = view.findViewById(R.id.LeggTilMedlemInput);
        listeOverMedlemmer = view.findViewById(R.id.listeOverMedlemmer);
        medlemDropdown = view.findViewById(R.id.kastUtMedlemDropdown);

        addUsersToDropdown();

        medlemDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = (User) parent.getItemAtPosition(position);
                System.out.println(selectedUser);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        kastUtMedlem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kickUserFromFamily();
            }
        });
    }

    private void kickUserFromFamily() {
        Cursor check = database.checkIfUserIsAdminOfFamily(sharedPreferences.getString(User.FAMILIE, null), sharedPreferences.getString(User.ID, null));

        if (check.getCount() > 0) {
            database.updateUserFamily(selectedUser.getId(), 0);
            addUsersToDropdown();
        } else
            Toast.makeText(getContext(),"Bare admin kan kaste ut andre brukere", Toast.LENGTH_SHORT).show();
        // Slette samtaler osv...
    }

    private void addUsersToDropdown() {
        Cursor data = database.getData();
        ArrayList<User> arrayList = new ArrayList<>();

        while(data.moveToNext()) {
            if (!data.getString(0).equals(sharedPreferences.getString(User.ID, null))
                    && data.getString(6).equals(sharedPreferences.getString(User.FAMILIE, null))) {
                int id = data.getInt(0);
                String name = data.getString(1);
                arrayList.add( new User(id,name));
            }
        }
        if (arrayList.size() > 0) {
            ArrayAdapter<User> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            medlemDropdown.setAdapter(adapter);
        }
    }
}