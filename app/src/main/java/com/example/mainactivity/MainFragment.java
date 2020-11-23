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
        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        // Initialize elements
        tablayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);
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