package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HandlelisteLeggTilFragment extends Fragment {

    public HandlelisteLeggTilFragment() {
        // Required empty constructor
    }

    // Variabler
    private Database database;
    private SharedPreferences sharedPreferences;
    private EditText Ukenr, item;
    private ListView varer;
    private Button leggTil, lagre;
    private ArrayAdapter<String> adapter;
    private ArrayList<HandlelisteVarerModel> vareliste = new ArrayList<>();
    private TextView empty;

    private String ukeInput, vareInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_handleliste_legg_til, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        Ukenr = view.findViewById(R.id.HandlelisteUkeNr);
        item = view.findViewById(R.id.item);
        varer = view.findViewById(R.id.HandlelisteListview);
        leggTil = view.findViewById(R.id.button);
        lagre = view.findViewById(R.id.Handlelisteopprett);
        empty = view.findViewById(R.id.emptyHandleliste);




        lagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ukeInput = Ukenr.getText().toString();
                database.weekHandleliste(ukeInput);
                navController.navigateUp();
            }
        });


        leggTil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vareInput = item.getText().toString();
                database.varerHandleliste(ukeInput, vareInput);
                adapter.add(vareInput);
                adapter.notifyDataSetChanged();
                item.setText("");

            }
        });



/*



 */

    }

}