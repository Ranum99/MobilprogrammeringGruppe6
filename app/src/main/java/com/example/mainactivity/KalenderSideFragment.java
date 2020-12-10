package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KalenderSideFragment extends Fragment {

    public KalenderSideFragment() {
        // Required empty public constructor
    }

    Database database;
    SharedPreferences sharedPreferences;

    private CalendarView calendar;
    private FloatingActionButton nyAktivitet;

    private String selectedDate;
    private ArrayList<KalenderSideModel> aktiviteter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kalender_side, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        calendar = view.findViewById(R.id.calendarView);

        // Setter dagens dato som den valgt datoen, slik at man vil se dagens aktiviteter fra start
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy");
        Date date = new Date();
        selectedDate = simpleDateFormat.format(date);

        // RecyclerView vil oppdatere seg dersom man trykker på en annen dato i CalenderView
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Date date = new Date((year-1900), month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd.MM.yyyy");

                selectedDate = simpleDateFormat.format(date);
                setActivitiesAndIds();
                setUpRecyclerView();
            }
        });

        // Henter aktiviteter og legger de inn i RecyclerView
        setActivitiesAndIds();
        setUpRecyclerView();

        nyAktivitet = view.findViewById(R.id.floatingActionButton);
        nyAktivitet.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_kalenderFragment2_to_kalenderLeggTilFragment));
    }

    private void setUpRecyclerView() {
        RecyclerView kalenderRecyclerView = getView().findViewById(R.id.activityOnDay);
        kalenderRecyclerView.setAdapter(new KalenderSideAdapter(getContext(), aktiviteter));
        kalenderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setActivitiesAndIds() {
        Cursor data = database.getData(Database.TABLE_CALENDAR_ACTIVITY);

        ArrayList<KalenderSideModel> aktiviteter = new ArrayList<>();

        while (data.moveToNext()) {
            int activityID = data.getInt(data.getColumnIndex(Database.COLUMN_ID));
            String dateFrom = data.getString(data.getColumnIndex(Database.COLUMN__CALENDAR_ACTIVITY_DATE_FROM));
            String dateTo = data.getString(data.getColumnIndex(Database.COLUMN__CALENDAR_ACTIVITY_DATE_TO));
            String timeFrom = data.getString(data.getColumnIndex(Database.COLUMN__CALENDAR_ACTIVITY_TIME_FROM));
            String timeTo = data.getString(data.getColumnIndex(Database.COLUMN__CALENDAR_ACTIVITY_TIME_TO));
            int userID = data.getInt(data.getColumnIndex(Database.COLUMN__CALENDAR_ACTIVITY_USER_ID));
            String theActivity = data.getString(data.getColumnIndex(Database.COLUMN__CALENDAR_ACTIVITY_ACTIVITY));

            // Lager gjør de om fra String til Date for å kunne bruke innebygde metoder
            String[] dateFromSplitted = dateFrom.split("\\.");
            Date fullDateFrom = new Date(Integer.parseInt(dateFromSplitted[2]) - 1900, Integer.parseInt(dateFromSplitted[1]) - 1, Integer.parseInt(dateFromSplitted[0]));

            // Lager gjør de om fra String til Date for å kunne bruke innebygde metoder
            String[] dateToSplitted;
            Date fullDateTo = null;
            if (dateTo != null) {
                dateToSplitted = dateTo.split("\\.");
                fullDateTo = new Date(Integer.parseInt(dateToSplitted[2]) - 1900, Integer.parseInt(dateToSplitted[1]) - 1, Integer.parseInt(dateToSplitted[0]));
            }

            // Lager gjør de om fra String til Date for å kunne bruke innebygde metoder
            String[] dateSelectedSplitted = selectedDate.split("\\.");
            Date fullDateSelected = new Date(Integer.parseInt(dateSelectedSplitted[2]) - 1900, Integer.parseInt(dateSelectedSplitted[1]) - 1, Integer.parseInt(dateSelectedSplitted[0]));

            // Henter brukers fulle navn og familie-ID
            Cursor userTo = database.getData(Database.TABLE_USER, userID);
            userTo.moveToFirst();
            String userName = userTo.getString(1);
            String userFamily = userTo.getString(6);

            // Dersom man har samme familie-ID vil den bli lagt til i listen
            if (userFamily.equals(sharedPreferences.getString(User.FAMILIE, null))){
                if (fullDateTo != null) { // Dersom det er en til dato og aktiviteten er lik eller mellom datoene blir den lagt med
                    if ((fullDateFrom.before(fullDateSelected) || fullDateFrom.equals(fullDateSelected)) && (fullDateTo.after(fullDateSelected) || fullDateTo.equals(fullDateSelected))) {
                        KalenderSideModel kalenderSideModel = new KalenderSideModel(dateFrom, dateTo, timeFrom, timeTo, userName, theActivity, userID, activityID, false);
                        aktiviteter.add(kalenderSideModel);
                    }
                } else if (fullDateFrom.equals(fullDateSelected)) { // Dersom det ikke er noe til dato, men aktiviteten er på datoen som er valgt blir den lagt med
                    KalenderSideModel kalenderSideModel = new KalenderSideModel(dateFrom, dateTo, timeFrom, timeTo, userName, theActivity, userID, activityID, false);
                    aktiviteter.add(kalenderSideModel);
                }
            }
        }

        this.aktiviteter = aktiviteter;
    }
}