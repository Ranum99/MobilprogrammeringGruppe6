package com.example.mainactivity.fragment;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mainactivity.Database;
import com.example.mainactivity.R;

public class BursdagRedigerFragment extends Fragment {

    public BursdagRedigerFragment() {
        // Required empty constructor
    }

    // Variabler
    private Database database;

    // Elementer i layouten
    private Button lagre;
    private EditText FullName;
    private DatePicker Birthday;
    private String name, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bursdag_rediger, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        // Instansierer variablene
        database = new Database(getActivity());
        lagre = view.findViewById(R.id.RedigerBursdagLagre);
        FullName = view.findViewById(R.id.BirthdayFultnavn);
        Birthday = view.findViewById(R.id.BirthdayDato);
        final String id = String.valueOf(getArguments().getString("ID"));

        String[] parts = getArguments().getString("DATO").split("\\.");
        Integer dag, maaned, aar;
        dag = Integer.parseInt(parts[0]);
        maaned = Integer.parseInt(parts[1]);
        aar = Integer.parseInt(parts[2]);
        Log.i("BursdagRedigerFragment", dag + "," + maaned + "," + aar);

        FullName.setText(getArguments().getString("NAVN"));
        Birthday.updateDate(aar, maaned-1, dag );
        Log.i("BursdagRedigerFragment", Birthday.getDayOfMonth() + "." + (Birthday.getMonth()+1) + "." + Birthday.getYear());
        Log.i("BursdagRedigerFragment", "ID: " + id);

        // Oppdaterer kolonnene i BIRTHDAY-tabellen i databasen med verdiene som er fylt ut.
        // Går tilbake til bursdagfragmentet
        lagre.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                // Henter inputen
                name = FullName.getText().toString();
                date = Birthday.getDayOfMonth() + "." + (Birthday.getMonth()+1) + "." + Birthday.getYear();

                // Sjekker at inputen er fylt inn korrekt
                if (name.length() == 0) {
                    // NAVN IKKE OK
                    Toast.makeText(getActivity(), "Fyll inn navn", Toast.LENGTH_SHORT).show();
                    Log.e("BursdagRedigerFragment", "Navn er ikke fylt inn korrekt");
                } else {
                    // NAVN OK
                    Log.i("BursdagRedigerFragment", "Nytt navn: " + name + ". Ny dato: " + date);

                    InputMethodManager mgr = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(FullName.getWindowToken(), 0);

                    database.updateBirthday(id, name, date);
                    navController.navigateUp();
                }
            }
        });
    }
}