package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FamilieOpprettFragment extends Fragment {

    private TextView navn, passord, passordIgjen;
    private Button opprettFamilie;
    Database database;
    SharedPreferences sharedPreferences;

    public FamilieOpprettFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_familie_opprett, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        final NavController navController = Navigation.findNavController(view);

        navn = view.findViewById(R.id.OpprettFamilieOpprettNavn);
        passord = view.findViewById(R.id.OpprettFamilieOpprettPassord);
        passordIgjen = view.findViewById(R.id.OpprettFamilieGjentaPassord);
        opprettFamilie = view.findViewById(R.id.OpprettFamilieBtn);

        opprettFamilie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passord.getText().toString().equals(passordIgjen.getText().toString())) {
                    database.addFamilyToDatabase(navn.getText().toString(), passord.getText().toString());
                                                                                                                            // MÅ FINNE ID ORDENTLIG
                    database.updateUserFamily(Integer.parseInt(sharedPreferences.getString(User.ID, null)), 1);
                    Toast.makeText(getActivity(), "Data successfully inserted", Toast.LENGTH_SHORT).show();


                    Bundle bundle = new Bundle();
                    bundle.putString("familieName", navn.getText().toString());
                    // MÅ SENDE MED RIKTIG ID, MEN HVORDAN????
                    bundle.putString("familieID", "1");

                    Navigation.findNavController(opprettFamilie).navigate(R.id.action_familieOpprettFragment_to_mainFragment, bundle);
                } else
                    Toast.makeText(getActivity(), "Passordene må være like", Toast.LENGTH_SHORT).show();
            }
        });
    }
}