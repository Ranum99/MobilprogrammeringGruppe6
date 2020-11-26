package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Objects;

public class HandlelisteFragment extends Fragment {

    public HandlelisteFragment() {}

    private Button NyHandleliste;
    Database database;
    SharedPreferences sharedPreferences;
    private ArrayList<String> overskriftHandleliste = new ArrayList<>();
    private ArrayList<String> varer = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_handleliste, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        setInfo();
        setUpRecyclerView();

        NyHandleliste = view.findViewById(R.id.NyHandleliste);
        NyHandleliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.handlelisteLeggTilFragment);
            }
        });
    }

    private void setInfo() {
        Cursor data = database.getData(Database.TABLE_HANDLELISTE);

        while(data.moveToNext()) {
            String overskriften = data.getString(data.getColumnIndex(Database.COLUMN_OVERSKRIFT_HANDLELISTE));
            String varene = data.getString(data.getColumnIndex(Database.COLUMN_VARER));

            overskriftHandleliste.add(overskriften);
            varer.add(varene);
        }
    }

    private void setUpRecyclerView() {
        RecyclerView handlelisteRecyclerView = requireView().findViewById(R.id.HandlelisteRecyclerview);
        handlelisteRecyclerView.setAdapter(new HandlelisteAdapter(getContext(), HandlelisteModel.getData(overskriftHandleliste, varer)));

        handlelisteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}