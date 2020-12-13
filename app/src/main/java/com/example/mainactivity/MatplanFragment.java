package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
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

public class MatplanFragment extends Fragment {

    // Variabler for å hente fra database
    private Database database;
    private SharedPreferences sharedPreferences;

    public MatplanFragment() {
        // Required empty constructor

    }

    // Elementer i layouten
    private FloatingActionButton nyMatplan;
    private TextView empty;
    private RecyclerView matplanRecyclerview;

    // ArrayList for å lagre dataen fra databasen
    private ArrayList<MatplanModel> matplaner = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matplan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        getFoodplans();
        nyMatplan = view.findViewById(R.id.NyMatplan);
        matplanRecyclerview = getView().findViewById(R.id.matplanRecyclerview);
        empty = view.findViewById(R.id.emptyMatplan);

        setUpRecyclerView();
        if (matplaner.isEmpty()) { empty.setVisibility(View.VISIBLE); }
        else { empty.setVisibility(View.GONE); }

        nyMatplan.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.matplanLeggTilFragment));
    }

    private void getFoodplans() {
        // Getting data from database table MATPLAN
        Cursor foodplansFromDB = database.getData(Database.TABLE_MATPLAN);

        ArrayList<MatplanModel> matplaner = new ArrayList<>();

        String familieID = sharedPreferences.getString(User.FAMILIE, null);

        while(foodplansFromDB.moveToNext()) {
            int matplanID = foodplansFromDB.getInt(foodplansFromDB.getColumnIndex(Database.COLUMN_ID));
            String fromDate = foodplansFromDB.getString(foodplansFromDB.getColumnIndex(Database.COLUMN_MATPLAN_FROM_DATE));
            String toDate = foodplansFromDB.getString(foodplansFromDB.getColumnIndex(Database.COLUMN_MATPLAN_TO_DATE));
            int familyID = foodplansFromDB.getInt(foodplansFromDB.getColumnIndex(Database.COLUMN_MATPLAN_FAMILY_ID));
            int week = foodplansFromDB.getInt(foodplansFromDB.getColumnIndex(Database.COLUMN_MATPLAN_UKE));

            if (familieID.equals(String.valueOf(familyID))) {
                MatplanModel matplan = new MatplanModel(matplanID, familyID, week, fromDate, toDate);
                matplaner.add(matplan);
            }
        }

        this.matplaner = matplaner;
    }

    private void setUpRecyclerView() {

        matplanRecyclerview.setAdapter(new MatplanAdapter(getContext(), matplaner));
        matplanRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}