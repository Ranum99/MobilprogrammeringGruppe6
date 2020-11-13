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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class FamilieboblaFragment extends Fragment {
    private EditText search;
    FamilieboblaSamtaleFragment fs;
    Database database;
    SharedPreferences sharedPreferences;
    private ArrayList<String> names, ids, samtaleNames;


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

        // Setting names and ids to global arrays
        setNamesAndIds();

        setUpRecyclerView();

        // Go to new samtale
        Button nySamtale = view.findViewById(R.id.FamilieboblaNySamtale);
        nySamtale.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_familieboblaFragment_to_familieboblaNySamtaleFragment));



        // Setting listener to btns on site (To delete a conversation)

        search = view.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //showElements();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setUpRecyclerView() {
        RecyclerView familieboblaRecyclerView = getView().findViewById(R.id.listOfConversations);
        familieboblaRecyclerView.setAdapter(new FamilieboblaAdapter(getContext(), FamilieboblaModel.getData(ids, names, samtaleNames)));

        familieboblaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setNamesAndIds() {
        // Getting data from database table CONVERSATION
        Cursor data = database.getData(Database.TABLE_CONVERSATION);

        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> ids = new ArrayList<>();
        ArrayList<String> samtaleNames = new ArrayList<>();

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
                        names.add(userToName);
                        ids.add(String.valueOf(conversationID));
                        samtaleNames.add(samtaleName);
                    }
                }
                if (toID == meID) {
                    // Getting name of user from
                    Cursor userFrom = database.getData(Database.TABLE_USER, fromID);
                    userFrom.moveToFirst();
                    String userFromName = userFrom.getString(1);
                    String userFromFamily = userFrom.getString(6);

                    if (userFromFamily.equals(sharedPreferences.getString(User.FAMILIE, null))) {
                        names.add(userFromName);
                        ids.add(String.valueOf(conversationID));
                        samtaleNames.add(samtaleName);
                    }
                }
            }
        }

        this.names = names;
        this.ids = ids;
        this.samtaleNames = samtaleNames;
    }



    private void showElements() {
        ConstraintLayout elementWraper = getActivity().findViewById(R.id.leggTilElementer);
        final int children = elementWraper.getChildCount();

        for (int i = 0; i < children; i++) {
            Button btn = (Button) elementWraper.getChildAt(i);
            String txtOfBtn = btn.getText().toString().toLowerCase();

            if (!txtOfBtn.contains(search.getText().toString().toLowerCase()))
                btn.setVisibility(View.GONE);
            else {
                btn.setVisibility(View.VISIBLE);

                //goToNewSiteListener(btn.getId(), FamilieboblaSamtale.class);
            }
        }
    }

}
