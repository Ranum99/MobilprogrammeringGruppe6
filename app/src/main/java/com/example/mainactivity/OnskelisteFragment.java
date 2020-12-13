package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class OnskelisteFragment extends Fragment {

    Database database;
    SharedPreferences sharedPreferences;

    public OnskelisteFragment() {
        // Required empty constructor
    }

    private FloatingActionButton nyOnskeliste;
    private TextView empty;
    private RecyclerView OnskelisteRecyclerview;
    // ArrayList for å lagre dataen fra databasen
    private ArrayList<OnskelisteModel> onskelister = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onskeliste, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        getWishlists();
        nyOnskeliste = view.findViewById(R.id.OpprettOnskelisteBtn);
        OnskelisteRecyclerview = getView().findViewById(R.id.OnskelisterRecyclerview);
        empty = view.findViewById(R.id.emptyOnskeliste);


        setUpRecyclerView();
        if (onskelister.isEmpty()) { empty.setVisibility(View.VISIBLE); }
        else { empty.setVisibility(View.GONE); }


        nyOnskeliste.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.onskelisteLeggTilFragment));
    }

    private void getWishlists() {
        // Getting data from database table WISHLIST
        Cursor wishlistsFromDB = database.getData(Database.TABLE_WISHLIST);

        ArrayList<OnskelisteModel> onskelister = new ArrayList<>();

        String familieID = sharedPreferences.getString(User.FAMILIE, null);

        while(wishlistsFromDB.moveToNext()) {
            int wishlistID = Integer.parseInt(wishlistsFromDB.getString(wishlistsFromDB.getColumnIndex(Database.COLUMN_ID)));
            int wishlistUserID = Integer.parseInt(wishlistsFromDB.getString(wishlistsFromDB.getColumnIndex(Database.COLUMN__USER_ID_WISHLIST)));
            String wishlistName = wishlistsFromDB.getString(wishlistsFromDB.getColumnIndex(Database.COLUMN__NAME_WISHLIST));

            Cursor wishlistUser = database.getData(Database.TABLE_USER, wishlistUserID);
            wishlistUser.moveToFirst();
            String userToName = wishlistUser.getString(1);
            String userToFamily = wishlistUser.getString(6);

            if (userToFamily.equals(familieID)) {
                OnskelisteModel onskeliste = new OnskelisteModel(wishlistID, userToName, wishlistUserID, wishlistName);
                onskelister.add(onskeliste);
            }
        }

        this.onskelister = onskelister;
    }

    private void setUpRecyclerView() {
        int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        RecyclerView OnskelisteRecyclerview = getView().findViewById(R.id.OnskelisterRecyclerview);
        OnskelisteRecyclerview.setAdapter(new OnskelisteAdapter(getContext(), onskelister, meID));
        OnskelisteRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
