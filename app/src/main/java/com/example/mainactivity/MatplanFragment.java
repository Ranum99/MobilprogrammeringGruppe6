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

import com.example.mainactivity.adapter.MatplanAdapter;
import com.example.mainactivity.model.MatplanModel;

public class MatplanFragment extends Fragment {

    public MatplanFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matplan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView matplanRecyclerview = getView().findViewById(R.id.matplanRecyclerview);
        matplanRecyclerview.setAdapter(new MatplanAdapter(getContext(), MatplanModel.getData()));

        matplanRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}