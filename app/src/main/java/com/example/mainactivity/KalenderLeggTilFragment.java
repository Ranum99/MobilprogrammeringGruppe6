package com.example.mainactivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class KalenderLeggTilFragment extends Fragment {

    public KalenderLeggTilFragment() {
    }

    Database database;
    SharedPreferences sharedPreferences;

    private Button btnAddActivity;
    private TextView txtDateFrom, txtDateTo, txtTimeFrom, txtTimeTo;
    private EditText txtActivity;
    private NavController navController;

    private Date fullDateFrom, fullDateTo;

    private String dateFrom, dateTo, theActivity, userID, timeFrom, timeTo;

    private int mYear, mMonth, mDay, mHour, mMinute;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kalender_legg_til, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        btnAddActivity = view.findViewById(R.id.btnAddActivity);
        txtDateFrom = view.findViewById(R.id.txtDateFrom);
        txtDateTo = view.findViewById(R.id.txtDateTo);
        txtTimeFrom = view.findViewById(R.id.txtTimeFrom);
        txtTimeTo = view.findViewById(R.id.txtTimeTo);
        txtActivity = view.findViewById(R.id.txtActivity);

        fullDateFrom = new Date(0,0,0,0,0,0);
        fullDateTo = new Date(0,0,0,0,0,0);

        txtDateFrom.setOnClickListener(new View.OnClickListener() {
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

                                fullDateFrom.setYear(year-1900);
                                fullDateFrom.setMonth(monthOfYear);
                                fullDateFrom.setDate(dayOfMonth);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy");

                                txtDateFrom.setText(simpleDateFormat.format(date));
                                dateFrom = simpleDateFormat.format(date);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtDateTo.setOnClickListener(new View.OnClickListener() {
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

                                fullDateTo.setYear(year-1900);
                                fullDateTo.setMonth(monthOfYear);
                                fullDateTo.setDate(dayOfMonth);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy");

                                txtDateTo.setText(simpleDateFormat.format(date));
                                dateTo = simpleDateFormat.format(date);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        txtTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Date time = new Date(0,0,0, hourOfDay, minute);

                                fullDateFrom.setHours(hourOfDay);
                                fullDateFrom.setMinutes(minute);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "HH:mm");

                                txtTimeFrom.setText(simpleDateFormat.format(time));
                                timeFrom = simpleDateFormat.format(time);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        txtTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                Date time = new Date(0,0,0, hourOfDay, minute);

                                fullDateTo.setHours(hourOfDay);
                                fullDateTo.setMinutes(minute);

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "HH:mm");

                                txtTimeTo.setText(simpleDateFormat.format(time));
                                timeTo = simpleDateFormat.format(time);
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

        btnAddActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addActivityToCalendar();

                System.out.println("DATE FROM: " + fullDateFrom);
                System.out.println("DATE TO: " + fullDateTo);

                System.out.println("\n\n");

                System.out.println("Date from: " + dateFrom);
                System.out.println("Date to: " + dateTo);
                System.out.println("Time from: " + timeFrom);
                System.out.println("Time to: " + timeTo);
                System.out.println("Bruker ID: " + sharedPreferences.getString(User.ID, null));
                System.out.println("Activity: " + txtActivity.getText().toString());
            }
        });
    }

    private void addActivityToCalendar() {
        int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        long addToDatabase = -1;

        if (sjekkInput())
            addToDatabase = database.addActivityToCalandar(dateFrom, dateTo, timeFrom, timeTo, meID, txtActivity.getText().toString());

        if (addToDatabase >= 0) {
            Toast.makeText(getContext(),"Added activity: " + txtActivity.getText().toString() + " to calendar", Toast.LENGTH_SHORT).show();
            navController.navigateUp();
        }
    }

    private boolean sjekkInput() {
        if (txtActivity.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Fyll inn aktivitet", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (dateFrom == null) {
            Toast.makeText(getActivity(), "Fyll inn dato fra", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (dateTo != null || timeTo != null) {
            if (dateTo == null) {
                fullDateTo.setYear(fullDateFrom.getYear());
                fullDateTo.setMonth(fullDateFrom.getMonth());
                fullDateTo.setDate(fullDateFrom.getDate());
            }
            if (fullDateFrom.after(fullDateTo)) {
                System.out.println(fullDateFrom);
                System.out.println(fullDateTo);
                Toast.makeText(getActivity(), "Dato/tid fra må være tidligere en dato/tid til", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
}