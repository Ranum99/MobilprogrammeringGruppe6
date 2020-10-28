package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {
    public MainFragment() {}

    private TabLayout tablayout;
    private ViewPager2 viewPager;
    private TabItem Hovedside, Gruppeinfo, Profil;
    public PageAdapter pagerAdapter;
    private Button LoggUt;
    private TextView FamilyName, FamilyId;
    private CardView Handleliste, Matplan, Bursdager, Kalender, Familiebobla, Onskeliste;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tablayout = view.findViewById(R.id.tabLayoutMain);
        LoggUt = view.findViewById(R.id.LoggUt);
        viewPager = view.findViewById(R.id.ViewPagerMain);

        Hovedside = view.findViewById(R.id.goToMainMain);
        Gruppeinfo  = view.findViewById(R.id.goToGruppeinformasjonMain);
        Profil = view.findViewById(R.id.goToProfileMain);

        FamilyName = view.findViewById(R.id.FamilyName);
        FamilyId = view.findViewById(R.id.FamilyId);
        Handleliste = view.findViewById(R.id.goToHandleliste);
        Matplan = view.findViewById(R.id.goToMatplan);
        Bursdager = view.findViewById(R.id.goToBursdager);
        Kalender = view.findViewById(R.id.goToKalender);
        Familiebobla = view.findViewById(R.id.goToFamiliebobla);
        Onskeliste = view.findViewById(R.id.goToOnskeliste);

        //viewPager.setAdapter(pagerAdapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        //pagerAdapter = new PageAdapter(getActivity().getSupportFragmentManager(), tablayout.getTabCount());

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pagerAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });



        /*
        Intent intent = requireActivity().getIntent();
        String string = intent.getStringExtra("message");
         */

        LoggUt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation Popup!").
                        setMessage("Are you sure that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Tømmer sharedPreferences
                                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                requireActivity().finish();

                                Intent intent1 = new Intent(requireActivity().getApplicationContext(), LoginFragment.class);
                                startActivity(intent1);

                            }
                        });
                builder.setNegativeButton("No",
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
}