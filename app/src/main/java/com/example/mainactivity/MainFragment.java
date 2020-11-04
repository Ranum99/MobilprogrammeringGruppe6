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
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mainactivity.adapter.PageAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainFragment extends Fragment {
    public MainFragment() {}

    private TabLayout tablayout;
    private ViewPager2 viewPager;
    public PageAdapter pagerAdapter;

    private TabItem Hovedside, Gruppeinfo, Profil;
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

        // Tabs
        tablayout = view.findViewById(R.id.tab_layout);
        Hovedside = view.findViewById(R.id.hovedside);
        Gruppeinfo  = view.findViewById(R.id.gruppeinfo);
        Profil = view.findViewById(R.id.profil);
        viewPager = view.findViewById(R.id.pager);

        FamilyName = view.findViewById(R.id.FamilyName);
        FamilyId = view.findViewById(R.id.FamilyId);
        Handleliste = view.findViewById(R.id.goToHandleliste);
        Matplan = view.findViewById(R.id.goToMatplan);
        Bursdager = view.findViewById(R.id.goToBursdager);
        Kalender = view.findViewById(R.id.goToKalender);
        Familiebobla = view.findViewById(R.id.goToFamiliebobla);
        Onskeliste = view.findViewById(R.id.goToOnskeliste);
        LoggUt = view.findViewById(R.id.LoggUt);

        pagerAdapter = new PageAdapter(getActivity().getSupportFragmentManager(), 3);
        viewPager.setAdapter(pagerAdapter);



        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    viewPager.getAdapter().notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    viewPager.getAdapter().notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    viewPager.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }


}