package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class MatplanListeFragment extends Fragment {
    // Variabler for å hente fra database
    private Database database;
    private SharedPreferences sharedPreferences;

    public MatplanListeFragment() {
    }

    private TextView overskrift;
    private RecyclerView matplanListeRecyclerview;

    private int matplanID, week;
    private ArrayList<MatplanListeModel> allFoodplans;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matplan_liste, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        overskrift = view.findViewById(R.id.overskriftMatplan);
        matplanListeRecyclerview = view.findViewById(R.id.MatplanListeRecyclerview);

        setMatplanID(getArguments().getInt("ID"));
        setWeek(getArguments().getInt("UKE"));

        String overskriften = "Matplan uke " + week;
        overskrift.setText(overskriften);

        getMatplans();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        matplanListeRecyclerview = getView().findViewById(R.id.MatplanListeRecyclerview);
        matplanListeRecyclerview.setAdapter(new MatplanListeAdapter(getContext(), allFoodplans));
        matplanListeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void getMatplans() {
        // Getting data from database table MESSAGES
        Cursor foodplans = database.getAllFoodplansForFoodplan(matplanID);

        ArrayList<MatplanListeModel> allFoodplans = new ArrayList<>();

        while(foodplans.moveToNext()) {
            int subMatplanID = foodplans.getInt(foodplans.getColumnIndex(Database.COLUMN_ID));
            int matplanID = foodplans.getInt(foodplans.getColumnIndex(Database.COLUMN__SUBMATPLAN_MATPLANID));
            String day = foodplans.getString(foodplans.getColumnIndex(Database.COLUMN__SUBMATPLAN_DAY));
            String food = foodplans.getString(foodplans.getColumnIndex(Database.COLUMN__SUBMATPLAN_FOOD));

            // Dersom samtaleID fra database og lokalt er like vil den bli lagt til i array
            if (matplanID == this.matplanID) {
                MatplanListeModel matplanData = new MatplanListeModel(subMatplanID, day, food);
                allFoodplans.add(matplanData);
            }
        }
        this.allFoodplans = allFoodplans;
    }

    public void setMatplanID(int matplanID) {
        this.matplanID = matplanID;
    }

    public void setWeek(int week) {
        this.week = week;
    }
}