package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FamilieboblaFragment extends Fragment {

    FamilieboblaSamtaleFragment fs;
    Database database;
    SharedPreferences sharedPreferences;

    private ArrayList<FamilieboblaModel> samtaler;
    private ArrayList<String> names, ids, samtaleNames;
    private FloatingActionButton nySamtale;
    private TextView empty;
    private RecyclerView familieboblaRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_familiebobla, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //this.view = view;
        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        empty = view.findViewById(R.id.emptySamtale);
        familieboblaRecyclerView = getView().findViewById(R.id.listOfConversations);
        nySamtale = view.findViewById(R.id.FamilieboblaNySamtale);

        // Setting names and ids to global arrays
        setNamesAndIds();
        setUpRecyclerView();

        if (samtaleNames.isEmpty()) { empty.setVisibility(View.VISIBLE); }
        else { empty.setVisibility(View.GONE); }

        // Go to new samtale
        nySamtale.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_familieboblaFragment_to_familieboblaNySamtaleFragment));
    }

    private void setUpRecyclerView() {
        RecyclerView familieboblaRecyclerView = getView().findViewById(R.id.listOfConversations);
        familieboblaRecyclerView.setAdapter(new FamilieboblaAdapter(getContext(), samtaler));
    }

    private void setNamesAndIds() {
        // Getting data from database table CONVERSATION
        Cursor data = database.getData(Database.TABLE_CONVERSATION);

        ArrayList<FamilieboblaModel> samtaler = new ArrayList<>();

        // Own id from session
        int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        while(data.moveToNext()) {
            int conversationID = Integer.parseInt(data.getString(data.getColumnIndex(Database.COLUMN_ID)));
            int fromID = Integer.parseInt(data.getString(data.getColumnIndex(Database.COLUMN__USER_FROM)));
            int toID = Integer.parseInt(data.getString(data.getColumnIndex(Database.COLUMN__USER_TO)));
            String samtaleName = data.getString(data.getColumnIndex(Database.COLUMN__CONVERSATION_NAME));

            if (fromID == meID || toID == meID) {
                // If I am person from set button name = person from
                if (fromID == meID) {
                    // Getting name of user to
                    Cursor userTo = database.getData(Database.TABLE_USER, toID);
                    userTo.moveToFirst();
                    String userToName = userTo.getString(1);
                    String userToFamily = userTo.getString(6);

                    if (userToFamily.equals(sharedPreferences.getString(User.FAMILIE, null))){
                        FamilieboblaModel samtale = new FamilieboblaModel(String.valueOf(conversationID), userToName, samtaleName);
                        samtaler.add(samtale);
                    }
                }
                if (toID == meID) {
                    // Getting name of user from
                    Cursor userFrom = database.getData(Database.TABLE_USER, fromID);
                    userFrom.moveToFirst();
                    String userFromName = userFrom.getString(1);
                    String userFromFamily = userFrom.getString(6);

                    if (userFromFamily.equals(sharedPreferences.getString(User.FAMILIE, null))) {
                        FamilieboblaModel samtale = new FamilieboblaModel(String.valueOf(conversationID), userFromName, samtaleName);
                        samtaler.add(samtale);
                    }
                }
            }
        }

        this.samtaler = samtaler;
    }

}
