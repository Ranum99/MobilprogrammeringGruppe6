package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OnskelisteFragment extends Fragment {

    public OnskelisteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onskeliste, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView OnskelisteRecyclerview = getView().findViewById(R.id.OnskelisterRecyclerview);
        OnskelisteRecyclerview.setAdapter(new OnskelisteAdapter(getContext(),OnskelisteModel.getData()));
        OnskelisteRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}