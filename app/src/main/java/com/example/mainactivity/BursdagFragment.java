package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class BursdagFragment extends Fragment {
    public BursdagFragment() {}

    private Button NyBursdag;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bursdag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        setUpRecyclerView();

        NyBursdag = view.findViewById(R.id.BursdagNyBursdag);
        NyBursdag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.bursdagLeggTilFragment);
            }
        });

    }

    private void setUpRecyclerView() {
        RecyclerView bursdagRecyclerView = getView().findViewById(R.id.BursdagRecyclerview);
        bursdagRecyclerView.setAdapter(new BirthdayAdapter(getContext(), BirthdayModel.getData()));

        bursdagRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}