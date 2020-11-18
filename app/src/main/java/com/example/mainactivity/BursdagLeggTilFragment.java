package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BursdagLeggTilFragment extends Fragment {

    public BursdagLeggTilFragment() {
        // Required empty constructor
    }

    // Variabler
    private Database database;

    // Elementer i layouten
    private Button lagre, avbryt;
    private EditText FullName;
    private EditText Birthday;

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
        FullName = view.findViewById(R.id.BirthdayFullName);
        Birthday = view.findViewById(R.id.BirthdayDate);

        // Lagrer informasjonen som er fylt ut i input-feltene i databasetabellen BIRTHDAY,
        // Går tilbake til bursdagfragmentet
        lagre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Henter inputen
                String name = FullName.getText().toString();
                String date = Birthday.getText().toString();

                if ( name.length() == 0) {
                    Toast.makeText(getActivity(), "Fyll inn navn", Toast.LENGTH_SHORT).show();
                    System.out.println("Navn er ikke fylt inn korrekt");
                } else {
                    System.out.println("Navn er fylt inn korrekt");
                    System.out.println(name);

                    if ( date.length() == 0 ) {
                        Toast.makeText(getActivity(), "Fyll inn dato", Toast.LENGTH_SHORT).show();

                        System.out.println(date);
                        System.out.println("dato er ikke fylt inn");
                    }
                    else if (date.length() != 10) {

                        Toast.makeText(getActivity(), "Datoen skal skrives på formen DD.MM.YYYY", Toast.LENGTH_SHORT).show();
                        System.out.println(date);
                        System.out.println("dato er ikke fylt inn korrekt");
                    }


                    // Sjekk om det er punktum der
                    else {
                        System.out.println("Noe er feil");
                    }
                }

                /*
                if (validUserInfo(name, date)) {
                    if (AddUser(name, date)) {
                        FullName.setText("");
                        Birthday.setText("");

                        navController.navigateUp();
                    }

                } else {
                    Toast.makeText(getActivity(), "You must put something in the text field", Toast.LENGTH_SHORT).show();
                }

                 */
            }
        });

        avbryt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });


    }

    private static String[] dato_formater = {
            "dd/MM/yyyy",
            "dd-MM-yyyy",
            "dd.MM.yyyy",
            "dd MM yyyy"
    };

    public static Date finnFormat (String sDate) {
        Date myDate = null;

        for (String formatString : dato_formater) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(formatString);
                format.setLenient(false);
                myDate = format.parse(sDate);
                break;
            }
            catch (ParseException e) {
                // System.out.println("  fmt: " + formatString + ": FAIL");
            }
        }
        return myDate;
    }
    /*
    public boolean AddUser(String name, String date) {
        boolean insertData = database.addUserToDatabaseBIRTHDAY(name, date);

        if (insertData) {
            Toast.makeText(getActivity(), "Data successfully inserted", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private boolean validUserInfo(String name, String date) {
        if (name.length() == 0 || date.length() == 0) {
            return false;
        }
        return true;
    }

     */
}