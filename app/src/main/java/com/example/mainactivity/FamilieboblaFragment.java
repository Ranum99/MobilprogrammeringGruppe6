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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FamilieboblaFragment extends Fragment {
    public FamilieboblaFragment() {}
    private EditText search;
    FamilieboblaSamtaleFragment fs;
    Database database;
    SharedPreferences sharedPreferences;
    //View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_familiebobla, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //this.view = view;
        setUpRecyclerView();
        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        Button nySamtale = view.findViewById(R.id.FamilieboblaNySamtale);
        nySamtale.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_familieboblaFragment_to_familieboblaNySamtaleFragment));

        //setConversationBtns();

        /*setListenerOnBtns();

        search = view.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showElements();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });*/
    }
    private void setUpRecyclerView() {
        RecyclerView familieboblaRecyclerView = getView().findViewById(R.id.listOfConversations);
        familieboblaRecyclerView.setAdapter(new FamilieboblaAdapter(getContext(), FamilieboblaModel.getData()));

        familieboblaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setConversationBtns() {
        Cursor data = database.getData(Database.TABLE_CONVERSATION);

        int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        System.out.println("ME: " + sharedPreferences.getString(User.NAME, null) + " (" + sharedPreferences.getString(User.ID, null) + ")");

        while(data.moveToNext()) {
            int fromID = data.getColumnIndex(Database.COLUMN__USER_FROM);
            int toID = data.getColumnIndex(Database.COLUMN__USER_TO);

            if (fromID == meID || toID == meID) {

                Cursor userFrom = database.getData(Database.TABLE_USER, fromID);
                userFrom.moveToFirst();
                String nameFrom = userFrom.getString(1);

                Cursor userTo = database.getData(Database.TABLE_USER, toID);
                userTo.moveToFirst();
                String nameTo = userTo.getString(1);

                if (fromID == meID) {
                    System.out.println("TO: " + nameTo + " (" + toID +")");
                }
                if (toID == meID) {
                    System.out.println("TO: " + nameFrom + " (" + fromID +")");
                }
            }
        }
    }

    private void setListenerOnBtns() {
        ConstraintLayout elementWrapper = getActivity().findViewById(R.id.leggTilElementer);
        final int children = elementWrapper.getChildCount();

        for (int i = 0; i < children; i++) {
            Button btn = (Button) elementWrapper.getChildAt(i);

            //goToNewSiteListener(btn.getId(), FamilieboblaSamtale.class);
        }
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