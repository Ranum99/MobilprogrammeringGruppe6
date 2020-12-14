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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainactivity.adapter.HandlelisteVareAdapter;
import com.example.mainactivity.model.HandlelisteVarerModel;

import java.util.ArrayList;

public class HandlelisteListeFragment extends Fragment {
    public HandlelisteListeFragment() {
        // Required empty constructor
    }

    private Database database;
    private SharedPreferences sharedPreferences;
    private ArrayList<HandlelisteVarerModel> varer;
    private TextView tittel;
    private Button leggTil;
    private EditText varelinje;
    private RecyclerView vareliste;
    private String id;
    private String sendtTittel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_handleliste_liste, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        tittel = view.findViewById(R.id.listeTittel);
        leggTil = view.findViewById(R.id.vareLeggTil);
        varelinje = view.findViewById(R.id.vareLinje);
        vareliste = view.findViewById(R.id.HandlelisteListeRecyclerView);

        id = getArguments().getString("ID");
        sendtTittel = getArguments().getString("TITTEL");

        tittel.setText(sendtTittel);

        leggTil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addVare();
            }
        });

        setInfo();
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        vareliste.setAdapter(new HandlelisteVareAdapter(getContext(), varer));
        vareliste.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setInfo() {
        Cursor data = database.getAlleVarerFraHandleliste(Integer.valueOf(id));

        ArrayList<HandlelisteVarerModel> allevarer = new ArrayList<>();

        while(data.moveToNext()) {
            String ID = data.getString(data.getColumnIndex(Database.COLUMN_ID));
            String vareTittel = data.getString(data.getColumnIndex(Database.COLUMN_HANDLELISTELISTE_VARE));
            boolean isChecked = data.getInt(data.getColumnIndex(Database.COLUMN_HANDLELISTELISTE_CHECKED)) == 1;

            HandlelisteVarerModel envare = new HandlelisteVarerModel(ID, vareTittel, isChecked);
            allevarer.add(envare);
        }
        this.varer = allevarer;
    }
    private void addVare() {
        long addToDatabase = -1;

        if( !varelinje.getText().toString().isEmpty()) {
            addToDatabase = database.addvarerHandleliste(varelinje.getText().toString(), id);
        }
        else {
            Toast.makeText(getContext(),"Du må skrive inn en vare først", Toast.LENGTH_SHORT).show();
        }

        if (addToDatabase >= 0) {
            Toast.makeText(getContext(),"Added vare: " + varelinje.getText().toString(), Toast.LENGTH_SHORT).show();
            varelinje.setText("");
            setInfo();
            setUpRecyclerView();
        }
        System.out.println(varelinje.getText().toString());
    }
}