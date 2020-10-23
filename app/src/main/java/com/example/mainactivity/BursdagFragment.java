package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class BursdagFragment extends Fragment {
    public BursdagFragment() {}

    ImageButton tilbake;
    Button NyBursdag;
    RecyclerView bursdag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bursdag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tilbake = view.findViewById(R.id.BursdagTilbake);
        tilbake.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_bursdagFragment_to_hovedsideFragment));
        NyBursdag = view.findViewById(R.id.BursdagNyBursdag);
        NyBursdag.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_bursdagFragment_to_bursdagLeggTilFragment));
        bursdag = view.findViewById(R.id.BursdagRecyclerview);
    }
}