package com.example.mainactivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MatplanLeggTilFragment extends Fragment {

    public MatplanLeggTilFragment() {
        // Required empty constructor
    }

    // Variabler
    private TextInputEditText fraDato, tilDato;
    private Button opprettBtn;
    Database database;
    SharedPreferences sharedPreferences;
    private NavController navController;

    private Date fromDate, toDate;
    private String dateFromString, dateToString;
    private int mYear, mMonth, mDay, familyID, matplanID;
    private String[] dagerIUka = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matplan_legg_til, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Instansierer variabler
        navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        fraDato = view.findViewById(R.id.txtDateFromMatplan);
        tilDato = view.findViewById(R.id.txtDateToMatplan);
        opprettBtn = view.findViewById(R.id.opprettMatplan);

        familyID = Integer.parseInt(sharedPreferences.getString(User.FAMILIE, null));

        fromDate = new Date();
        toDate = new Date();

        fraDato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Date date = new Date((year-1900), monthOfYear, dayOfMonth);

                                fromDate.setYear(year-1900);
                                fromDate.setMonth(monthOfYear);
                                fromDate.setDate(dayOfMonth);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy");

                                fraDato.setText(simpleDateFormat.format(date));
                                dateFromString = simpleDateFormat.format(date);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        tilDato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                Date date = new Date((year-1900), monthOfYear, dayOfMonth);

                                toDate.setYear(year-1900);
                                toDate.setMonth(monthOfYear);
                                toDate.setDate(dayOfMonth);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy");

                                tilDato.setText(simpleDateFormat.format(date));
                                dateToString = simpleDateFormat.format(date);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        opprettBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateFromString.isEmpty() || dateToString.isEmpty()) {
                    Toast.makeText(getActivity(), "Fyll inn alle feltene", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (toDate.before(fromDate)) {
                    Toast.makeText(getActivity(), "Til dato må være før/lik som fra dato", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar calFrom = Calendar.getInstance();
                Calendar calTo = Calendar.getInstance();
                calFrom.setTime(fromDate);
                calTo.setTime(toDate);

                if (calFrom.get(Calendar.WEEK_OF_YEAR) != calTo.get(Calendar.WEEK_OF_YEAR)) {
                    Toast.makeText(getActivity(), "Datoene må være i samme uke", Toast.LENGTH_SHORT).show();
                    return;
                }
                int week = calFrom.get(Calendar.WEEK_OF_YEAR);
                int daysBetween = (int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24)) + 1;

                boolean addToDatabase = false;

                if (sjekkOfMatplanFinnesFraFor(week))
                    addToDatabase = database.addWeekToMatplan(dateFromString, dateToString, familyID, week);

                Cursor matplanIdQuery = database.getMatplanIdByLastRow();
                matplanID = 1;
                while(matplanIdQuery.moveToNext()) {
                    matplanID = Integer.parseInt(matplanIdQuery.getString(0));
                }

                if (addToDatabase) {
                    Toast.makeText(getActivity(), "Matplan i uke " + week + " er opprettet", Toast.LENGTH_SHORT).show();
                    addDaysToMatplan(matplanID, daysBetween);
                    navController.navigateUp();
                } else {
                    Toast.makeText(getActivity(), "Matplan i uke " + week + " finnes allerede fra før på disse datoene", Toast.LENGTH_SHORT).show();
                }
            }

            private void addDaysToMatplan(int matplanID, int daysBetween) {
                //Cursor addDaysToMatplan = database.addDaysToMatplan(matplanID);
                Date date = fromDate;

                for (int i = 0; i < daysBetween; i++) {
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    String dateOnStringForm = date.getDate() + "." + (date.getMonth() + 1) + "." + (date.getYear() + 1900);

                    String day = dagerIUka[cal.get(Calendar.DAY_OF_WEEK) - 1];

                    database.makeSubMatplan(matplanID, day, dateOnStringForm, familyID);

                    date.setDate(date.getDate() + 1);
                }
            }

            private boolean sjekkOfMatplanFinnesFraFor(int week) {
                Cursor insertData = database.getData(Database.TABLE_MATPLAN);

                while(insertData.moveToNext()) {
                    String uke = insertData.getString(insertData.getColumnIndex(Database.COLUMN_MATPLAN_UKE));
                    String fromDate = insertData.getString(insertData.getColumnIndex(Database.COLUMN_MATPLAN_FROM_DATE));
                    String toDate = insertData.getString(insertData.getColumnIndex(Database.COLUMN_MATPLAN_TO_DATE));
                    String familieID = insertData.getString(insertData.getColumnIndex(Database.COLUMN_MATPLAN_FAMILY_ID));

                    if (familieID.equals(String.valueOf(familyID)) && uke.equals(String.valueOf(week)) && fromDate.equals(dateFromString) && toDate.equals(dateToString)) {
                        return false;
                    }
                }

                return true;
            }
        });


    }
}