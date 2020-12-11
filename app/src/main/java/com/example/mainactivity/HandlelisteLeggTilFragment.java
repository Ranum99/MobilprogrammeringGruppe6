package com.example.mainactivity;

import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HandlelisteLeggTilFragment extends Fragment {

    public HandlelisteLeggTilFragment() {
        // Required empty constructor
    }

    private Database database;
    private SharedPreferences sharedPreferences;
    private EditText tittel;
    private Button opprett;

    private int meID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_handleliste_legg_til, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = getContext().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        tittel = view.findViewById(R.id.handlelisteTittel);
        opprett = view.findViewById(R.id.lagHandleliste);

        opprett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tittel.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Du må sette en tittel på handlelisten", Toast.LENGTH_SHORT).show();
                }
                else {
                    database.weekHandleliste(tittel.getText().toString(), meID);
                    System.out.println(tittel.getText().toString());

                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);

                    mgr.hideSoftInputFromWindow(tittel.getWindowToken(), 0);
                    navController.navigateUp();

                }
            }
        });







/*



 */

    }

}