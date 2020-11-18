package com.example.mainactivity;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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

import com.google.android.material.datepicker.CalendarConstraints;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiresApi(api = Build.VERSION_CODES.O)
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
    private String name, date, regEx;
    private Integer splitAar, splitMaaned, splitDag;
    private LocalDate today, dato;
    private Period period;

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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Henter inputen
                name = FullName.getText().toString();
                date = Birthday.getText().toString();
                regEx = "[0-9][0-9]\\.[0-9][0-9]\\.[0-9][0-9][0-9][0-9]";

                // Sjekker at inputen er fylt inn korrekt
                if (name.length() == 0) {
                    // NAVN IKKE OK
                    Toast.makeText(getActivity(), "Fyll inn navn", Toast.LENGTH_SHORT).show();
                    System.out.println("Navn er ikke fylt inn korrekt");
                } else {
                    // NAVN OK
                    System.out.println("Navn er fylt inn korrekt: " + name);

                    if (date.length() == 0) {
                        // DATO IKKE OK
                        Toast.makeText(getActivity(), "Fyll inn dato", Toast.LENGTH_SHORT).show();
                        System.out.println("Dato er ikke fylt inn");
                    }
                    else if (date.length() != 10) {
                        // DATO IKKE OK
                        Toast.makeText(getActivity(), "Datoen er ikke fylt inn korrekt", Toast.LENGTH_SHORT).show();
                        System.out.println("Dato har ikke riktig antall tall: " + date);
                    }
                    else if (sjekkFormat(date) == false) {
                        // DATO IKKE OK
                        System.out.println("Dato er ikke skrevet på formen dd.mm.yyyy: " + date);
                    }
                    else {
                        // DATO FORMAT OK
                        System.out.println("Dato er skrevet på riktig form: " + date);
                        String[] parts = date.split("\\.");

                        splitAar = Integer.parseInt(parts[2]);
                        splitMaaned = Integer.parseInt(parts[1]);
                        splitDag = Integer.parseInt(parts[0]);

                        today = LocalDate.now();
                        dato = LocalDate.of(splitAar, splitMaaned, splitDag);
                        period = Period.between(dato, today);


                        if (isValid(date) == false) {
                            Toast.makeText(getActivity(), "Ikke gyldig dato", Toast.LENGTH_SHORT).show();
                            System.out.println("Ikke gyldig dato");
                        }
                        else {
                            System.out.println("Bare sjekk av gyldig dato igjen");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
                            LocalDate dato = LocalDate.parse(date, formatter);

                            System.out.println("Formatert dato: " + dato);

                            if (dato.isAfter(today)) {
                                Toast.makeText(getActivity(), "Kan ikke være født i fremtiden", Toast.LENGTH_SHORT).show();
                                System.out.println("Dato skrevet inn: " + dato);
                                System.out.println("FEIL");
                            }
                            else {

                                System.out.println("Dato skrevet inn: " + dato);
                                System.out.println("RIKTIG");
                                //database.addUserToDatabaseBIRTHDAY(name, date);
                                //navController.navigateUp();
                            }
                        }
                    }
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

    private boolean sjekkFormat(String date) {
        Pattern pattern = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(date);
        boolean matchFound = matcher.find();
        if(matchFound) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValid(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.uuuu");
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}