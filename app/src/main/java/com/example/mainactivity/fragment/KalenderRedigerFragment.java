package com.example.mainactivity.fragment;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mainactivity.Database;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KalenderRedigerFragment extends Fragment {
    public KalenderRedigerFragment() {
    }

    Database database;
    SharedPreferences sharedPreferences;

    private Button btnChangeActivity;
    private TextView txtDateFrom, txtDateTo, txtTimeFrom, txtTimeTo;
    private EditText txtActivity;
    private NavController navController;

    private Date fullDateFrom, fullDateTo;
    private String dateFrom, dateTo, timeFrom, timeTo;
    private int mYear, mMonth, mDay, mHour, mMinute, activityID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kalender_endre, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Instansierer variabler
        navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        btnChangeActivity = view.findViewById(R.id.btnChangeActivity);
        txtDateFrom = view.findViewById(R.id.txtDateFrom);
        txtDateTo = view.findViewById(R.id.txtDateTo);
        txtTimeFrom = view.findViewById(R.id.txtTimeFrom);
        txtTimeTo = view.findViewById(R.id.txtTimeTo);
        txtActivity = view.findViewById(R.id.txtActivity);

        fullDateFrom = new Date();
        fullDateTo = new Date();

        // Henter fra budle dersom den ikke er null
        setActivity(getArguments().getString("theActivity"));
        setDateFrom(getArguments().getString("dateFrom"));

        if (getArguments().getString("dateTo") != null) {
            setDateTo(getArguments().getString("dateTo"));
        }

        if (getArguments().getString("timeFrom") != null) {
            setTimeFrom(getArguments().getString("timeFrom"));
        }

        if (getArguments().getString("timeTo") != null) {
            setTimeTo(getArguments().getString("timeTo"));
        }

        setActivityID(getArguments().getInt("activityID"));

        // Her henter man er DatePicker, og gjør at bruker kan velge en dato
        txtDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mYear = fullDateFrom.getYear() + 1900;
                mMonth = fullDateFrom.getMonth();
                mDay = fullDateFrom.getDate();

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

        // Her henter man er DatePicker, og gjør at bruker kan velge en dato
        txtDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateTo == null) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                } else {
                    mYear = fullDateTo.getYear() + 1900;
                    mMonth = fullDateTo.getMonth();
                    mDay = fullDateTo.getDate();
                }

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

        // Her henter man er TimePicker, og gjør at bruker kan velge en tid
        txtTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeFrom == null) {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                } else {
                    mHour = fullDateFrom.getHours();
                    mMinute = fullDateFrom.getMinutes();
                }

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

        // Her henter man er TimePicker, og gjør at bruker kan velge en tid
        txtTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timeTo == null) {
                    final Calendar c = Calendar.getInstance();
                    mHour = c.get(Calendar.HOUR_OF_DAY);
                    mMinute = c.get(Calendar.MINUTE);
                } else {
                    mHour = fullDateTo.getHours();
                    mMinute = fullDateTo.getMinutes();
                }

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
        btnChangeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeActivityToCalendar();
            }
        });
    }

    // Endrer i databasen
    private void changeActivityToCalendar() {
        boolean changeInDatabase = false;

        if (sjekkInput())
            changeInDatabase = database.editActivityInCalandar(activityID, dateFrom, dateTo, timeFrom, timeTo, txtActivity.getText().toString());

        if (changeInDatabase) {
            Log.i("KalenderRediger", "Changed activity: " + txtActivity.getText().toString() + " in calendar");
            navController.navigateUp();
        }
    }

    // Sjekker at:
    // Man har fylt inn en aktivitet
    // Man har fylt ut en dato fra (dato til, tid fra og tid til kan stå tomme dersom brukeren vil det)
    // Til slutt sjekker den om tid fra (dato og tid) er før tid til
    private boolean sjekkInput() {
        if (txtActivity.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Fyll inn aktivitet", Toast.LENGTH_SHORT).show();
            Log.e("KalenderRediger", "Bruker fylte ikke inn en aktivitet");
            return false;
        }
        if (dateFrom == null) {
            Toast.makeText(getActivity(), "Fyll inn dato fra", Toast.LENGTH_SHORT).show();
            Log.e("KalenderRediger", "Bruker fylte ikke inn dato fra");
            return false;
        }
        if (timeFrom == null) {
            fullDateFrom.setHours(0);
            fullDateFrom.setMinutes(0);
            fullDateFrom.setSeconds(0);
        }

        if (dateTo != null || timeTo != null) {
            if (dateTo == null) {
                fullDateTo.setYear(fullDateFrom.getYear());
                fullDateTo.setMonth(fullDateFrom.getMonth());
                fullDateTo.setDate(fullDateFrom.getDate());
            }
            if (fullDateFrom.after(fullDateTo)) {
                Toast.makeText(getActivity(), "Dato/tid fra må være tidligere en dato/tid til", Toast.LENGTH_SHORT).show();
                Log.e("KalenderRediger", "Bruker fylte inn dato/tid som er tidligere enn dato/tid til");
                return false;
            }
        }
        return true;
    }

    public void setActivity(String txtActivity) {
        this.txtActivity.setText(txtActivity);
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
        this.txtDateFrom.setText(dateFrom);
        setFullDateFrom(dateFrom);
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
        this.txtDateTo.setText(dateTo);
        setFullDateTo(dateTo);
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
        this.txtTimeFrom.setText(timeFrom);

        String[] timeSplitted = timeFrom.split(":");
        fullDateFrom.setHours(Integer.parseInt(timeSplitted[0]));
        fullDateFrom.setMinutes(Integer.parseInt(timeSplitted[1]));
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
        this.txtTimeTo.setText(timeTo);

        System.out.println(timeTo);

        String[] timeSplitted = timeTo.split(":");
        fullDateTo.setHours(Integer.parseInt(timeSplitted[0]));
        fullDateTo.setMinutes(Integer.parseInt(timeSplitted[1]));
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public void setFullDateFrom(String dateFrom) {
        String[] fullDateSplitted = dateFrom.split("\\.");
        fullDateFrom.setDate(Integer.parseInt(fullDateSplitted[0]));
        fullDateFrom.setMonth(Integer.parseInt(fullDateSplitted[1]) - 1);
        fullDateFrom.setYear(Integer.parseInt(fullDateSplitted[2]) - 1900);
    }

    public void setFullDateTo(String dateTo) {
        String[] fullDateSplitted = dateTo.split("\\.");
        fullDateTo.setDate(Integer.parseInt(fullDateSplitted[0]));
        fullDateTo.setMonth(Integer.parseInt(fullDateSplitted[1]) - 1);
        fullDateTo.setYear(Integer.parseInt(fullDateSplitted[2]) - 1900);
    }
}