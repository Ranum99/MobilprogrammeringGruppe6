package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainFragment extends Fragment {
    public MainFragment() {}


    private TabLayout tablayout;
    private ViewPager2 viewPager;
    private PageAdapter pagerAdapter;
    private Button loggut;
    private TextView familyName, familyId;
    SharedPreferences sharedPreferences;
    Database database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        // Initialize elements
        tablayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);
        loggut = view.findViewById(R.id.LoggUtMain);
        familyName = view.findViewById(R.id.FamilyNameMain);
        familyId = view.findViewById(R.id.FamilyIdMain);

        String familieNavnet = getFamilyName();

        familyName.setText(familieNavnet);
        familyId.setText("Familie-ID: " + sharedPreferences.getString(User.FAMILIE, null));

        //TabLayout
        pagerAdapter = new PageAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(tablayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0:
                        tab.setText("Hovedside");
                        break;
                    case 1:
                        tab.setText("GruppeInfo");
                        break;
                    case 2:
                        tab.setText("Profil");
                        break;
                }

            }
        }).attach();

        //Logg ut

        loggut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Nei, ikke gå!!!").
                        setMessage("Er du sikker på at du vil logge ut?");
                builder.setPositiveButton("Ja, jeg vil ut herifra!",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Tømmer sharedPreferences
                                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                navController.navigate(R.id.loginFragment);
                            }
                        });
                builder.setNegativeButton("Nei, ta meg tilbake!",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });
    }

    private String getFamilyName() {
        String name = "";
        Cursor familieNavnetQuery = database.getData(Database.TABLE_FAMILY, Integer.parseInt(sharedPreferences.getString(User.FAMILIE, null)));
        while(familieNavnetQuery.moveToNext()) {
            name = familieNavnetQuery.getString(1);
        }
        return name;
    }


}