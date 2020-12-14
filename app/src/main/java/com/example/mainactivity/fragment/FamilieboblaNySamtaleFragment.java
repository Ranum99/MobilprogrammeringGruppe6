package com.example.mainactivity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainactivity.Database;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;

import java.util.ArrayList;

public class FamilieboblaNySamtaleFragment extends Fragment {
    public FamilieboblaNySamtaleFragment() {}

    SharedPreferences sharedPreferences;
    Database database;

    private User selectedUser;
    private Spinner spinner;
    private NavController navController;
    private TextView conversationName;
    private Button addSamtale;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_familiebobla_ny_samtale, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        spinner = view.findViewById(R.id.users);
        addSamtale = view.findViewById(R.id.lagSamtale);
        conversationName = view.findViewById(R.id.conversationName);

        // Fyller dropdown med familiemedlemmer
        addUsersToDropdown();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedUser = (User) parent.getItemAtPosition(position);
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        addSamtale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUser != null)
                    makeNewSamtale();
                else
                    Toast.makeText(getActivity(), "Du må velge en person å starte samtale med", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Lager en ny samtale med et annet familiemedlem dersom samtalenavn er fylt ut
    private void makeNewSamtale() {
        int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        long addToDatabase = -1;

        if (!conversationName.getText().toString().isEmpty())
            addToDatabase = database.makeNewConversation(meID, selectedUser, conversationName.getText().toString());
        else
            Toast.makeText(getActivity(), "Du må sette et navn til samtalen", Toast.LENGTH_SHORT).show();

        if (addToDatabase >= 0) {
            Toast.makeText(getContext(),"Made conversation with: " + selectedUser.getName() + ", named: " + conversationName.getText(), Toast.LENGTH_SHORT).show();
            navController.navigateUp();
        }
        System.out.println("New conversation with: " + selectedUser);
    }

    private void addUsersToDropdown() {
        Cursor data = database.getData();

        ArrayList<User> arrayList = new ArrayList<>();

        while(data.moveToNext()) {
            if (data.getString(6) != null) { // Sjekker at familie kolonnen i databasen ikke er tom
                // Meg-bruker blir ikke lagt til i listen, og bare andre medlemmer av samme familie blir lagt til
                if (!data.getString(0).equals(sharedPreferences.getString(User.ID, null)) && data.getString(6).equals(sharedPreferences.getString(User.FAMILIE, null))) {
                    int id = data.getInt(0);
                    String name = data.getString(1);
                    arrayList.add( new User(id, name));
                }
            }
        }
        // Så lenge listen ikke er tom blir spinneren fylt med brukere
        if (arrayList.size() > 0) {
            ArrayAdapter<User> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, arrayList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }
    }
}