package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mainactivity.adapter.PageAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class KalenderFragment extends Fragment {
    public KalenderFragment() {
        // Required empty constructor
    }

    private TabLayout tablayout;
    private ViewPager2 viewPager;
    private PageAdapter pagerAdapter;

    SharedPreferences sharedPreferences;
    Database database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_kalender, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        // Initialize elements
        tablayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);

        pagerAdapter = new PageAdapter(this);
        viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(tablayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch(position) {
                    case 0:
                        tab.setText("Kalender");
                        break;
                    case 1:
                        tab.setText("Bursdager");
                        break;
                }
            }
        }).attach();
    }
}