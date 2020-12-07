package com.example.mainactivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;


@RequiresApi(api = Build.VERSION_CODES.O)
public class BursdagLeggTilFragment extends Fragment {

    public BursdagLeggTilFragment() {
        // Required empty constructor
    }

    // Variabler
    Database database;
    SharedPreferences sharedPreferences;

    // Elementer i layouten
    private Button lagre, avbryt;
    private EditText FullName;
    private DatePicker Birthday;
    private String name, date, familieId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bursdag_legg_til, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        // Instansierer variablene
        database = new Database(getActivity());
        lagre = view.findViewById(R.id.NyBursdagLagre);
        avbryt = view.findViewById(R.id.NyBursdagAvbryt);
        FullName = view.findViewById(R.id.BirthdayFullname);
        Birthday = view.findViewById(R.id.BirthdayDate);
        Birthday.setMaxDate(System.currentTimeMillis());

        lagre.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Henter inputen
                name = FullName.getText().toString();
                date = Birthday.getDayOfMonth() + "." + (Birthday.getMonth()+1) + "." + Birthday.getYear();

                // Henter familieId på brukeren
                familieId =  sharedPreferences.getString(User.FAMILIE, null);

                // Sjekker at inputen er fylt inn korrekt
                if (name.length() == 0) {
                    // NAVN IKKE OK
                    Toast.makeText(getActivity(), "Fyll inn navn", Toast.LENGTH_SHORT).show();
                    System.out.println("Navn er ikke fylt inn korrekt");
                } else {
                    // NAVN OK
                    System.out.println("Navn er fylt inn korrekt: " + name);
                    System.out.println(date);

                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(FullName.getWindowToken(), 0);

                    // Lagrer informasjonen som er fylt ut i input-feltene i databasetabellen BIRTHDAY,
                    database.addUserToDatabaseBIRTHDAY(name, date, familieId);
                    // Går tilbake til bursdagfragmentet
                    navController.navigateUp();
                }
            }
        });

        avbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
    }

}