package com.example.mainactivity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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

import com.example.mainactivity.Database;
import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;

import java.util.ArrayList;

public class GruppeinformasjonFragment extends Fragment {
    public GruppeinformasjonFragment() {}

    SharedPreferences sharedPreferences;
    Database database;

    private Button endreFamilieNavn, kastUtMedlem;
    private EditText familieNavnInput;
    private ListView listeOverMedlemmer;
    private Spinner medlemDropdown;
    private User selectedUser;
    private ArrayList<User> usersInFamily = new ArrayList<>();

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
        kastUtMedlem = view.findViewById(R.id.KastUtMedlemBtn);
        familieNavnInput = view.findViewById(R.id.EndreFamilieNavnInput);
        listeOverMedlemmer = view.findViewById(R.id.listeOverMedlemmer);
        medlemDropdown = view.findViewById(R.id.kastUtMedlemDropdown);

        // Henter familiemedlemmer fra database
        usersInFamily = fillUsersInFamily();

        // Fyller familiemedlemmer i dropdown og liste
        addUsersToDropdown(usersInFamily);
        addUsersToListView(usersInFamily);

        medlemDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = (User) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        kastUtMedlem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String familyID = sharedPreferences.getString(User.FAMILIE, null);
                String myID = sharedPreferences.getString(User.ID, null);

                kickUserFromFamily(familyID, myID);
            }
        });

        endreFamilieNavn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int familyID = Integer.parseInt(sharedPreferences.getString(User.FAMILIE, null));
                String newFamilyName = familieNavnInput.getText().toString();
                String myID = sharedPreferences.getString(User.ID, null);

                changeFamilyName(familyID, newFamilyName, myID);
            }
        });
    }

    // Bare en admin kan kicke en annen bruker
    private void kickUserFromFamily(String familyID, String myID) {
        Cursor check = database.checkIfUserIsAdminOfFamily(familyID, myID);

        if (selectedUser == null) {
            Toast.makeText(getContext(),"Du må velge en bruker å kaste ut", Toast.LENGTH_SHORT).show();
            Log.e("Gruppeinformasjon", "Bruker valgte ikke en bruker å kaste ut");
            return;
        }
        if (check.getCount() > 0) {
            boolean updateCheck = database.updateUserFamily(selectedUser.getId(), null);
            if (updateCheck) {
                usersInFamily = fillUsersInFamily();
                addUsersToDropdown(usersInFamily);
                addUsersToListView(usersInFamily);
                database.updateBirthdayFAMILY(String.valueOf(selectedUser.id), null);
                Log.i("Gruppeinformasjon", "Kastet ut " + selectedUser.getName());
            } else {
                Toast.makeText(getContext(),"Kunne ikke kaste ut " + selectedUser.getName(), Toast.LENGTH_SHORT).show();
                Log.e("Gruppeinformasjon", "Kunne ikke kaste ut " + selectedUser.getName());
            }
        } else
            Toast.makeText(getContext(),"Bare admin kan kaste ut andre brukere", Toast.LENGTH_SHORT).show();
            Log.w("Gruppeinformasjon", "Bare admin kan kaste ut andre brukere");
    }

    //Bare en admin kan bytte familienavn
    private void changeFamilyName(int familyID, String newFamilyName, String myID) {
        Cursor check = database.checkIfUserIsAdminOfFamily(String.valueOf(familyID), myID);

        if (check.getCount() > 0) {
            boolean kickCheck = database.updateFamilyName(familyID, newFamilyName);
            if (kickCheck) {
                Log.i("Gruppeinformasjon", "Familienavnet ble endret til " + newFamilyName);
                familieNavnInput.setText("");
                MainActivity.setText(newFamilyName);
            } else
                Toast.makeText(getContext(),"Kunne ikke endre familienavnet", Toast.LENGTH_SHORT).show();
                Log.e("Gruppeinformasjon", "Kunne ikke endre familienavnet");
        } else
            Toast.makeText(getContext(),"Bare admin kan endre familienavnet", Toast.LENGTH_SHORT).show();
            Log.w("Gruppeinformasjon", "Bare admin kan endre familienavnet");
    }

    private void addUsersToDropdown(ArrayList<User> usersInFamily) {
        ArrayAdapter<User> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, usersInFamily);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        medlemDropdown.setAdapter(adapter);
    }

    private void addUsersToListView(ArrayList<User> usersInFamily) {
        ArrayList<User> arrayList = new ArrayList<>();

        User me = new User(Integer.parseInt(sharedPreferences.getString(User.ID, null)), sharedPreferences.getString(User.NAME, null));

        // Setter meg selv som øverste på listen
        arrayList.add(me);
        arrayList.addAll(usersInFamily);

        ArrayAdapter<User> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, arrayList);

        listeOverMedlemmer.setAdapter(arrayAdapter);
    }

    // Henter medlemmer av familie
    private ArrayList<User> fillUsersInFamily() {
        Cursor data = database.getData();
        ArrayList<User> arrayList = new ArrayList<>();

        while(data.moveToNext()) {
            if (data.getString(6) != null) {
                if (!data.getString(0).equals(sharedPreferences.getString(User.ID, null))
                        && data.getString(6).equals(sharedPreferences.getString(User.FAMILIE, null))) {
                    int id = data.getInt(0);
                    String name = data.getString(1);
                    arrayList.add( new User(id,name));
                }
            }
        }
        return arrayList;
    }
}