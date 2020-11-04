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

import com.example.mainactivity.adapter.HandlelisteAdapter;
import com.example.mainactivity.model.HandlelisteModel;

public class HandlelisteFragment extends Fragment {

    public HandlelisteFragment() {}

    private Button NyHandleliste;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_handleliste, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);
        setUpRecyclerView();

        NyHandleliste = view.findViewById(R.id.NyHandleliste);
        NyHandleliste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.handlelisteLeggTilFragment);
            }
        });

    }
    private void setUpRecyclerView() {
        RecyclerView handlelisteRecyclerView = getView().findViewById(R.id.HandlelisteRecyclerview);
        handlelisteRecyclerView.setAdapter(new HandlelisteAdapter(getContext(), HandlelisteModel.getData()));

        handlelisteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}