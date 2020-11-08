package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class FamilieboblaSamtaleFragment extends Fragment {

    public FamilieboblaSamtaleFragment() {}

    private int samtaleId;
    private String samtaleName, samtaleTo;
    private ImageButton delete;
    private FamilieboblaAdapter familieboblaAdapter;

    private TextView samtaleTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_familiebobla_samtale, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setSamtaleId(Integer.parseInt(getArguments().getString("samtaleId")));
        setSamtaleName(getArguments().getString("samtaleName"));
        setSamtaleTo(getArguments().getString("samtaleTo"));

        String text = samtaleName + " (" + samtaleTo + ")";

        samtaleTitle = view.findViewById(R.id.SamtaleBrukernavn);
        samtaleTitle.setText(text);
    }

    public void setSamtaleId(int samtaleId) {
        this.samtaleId = samtaleId;
    }

    public void setSamtaleName(String samtaleName) {
        this.samtaleName = samtaleName;
    }

    public void setSamtaleTo(String samtaleTo) {
        this.samtaleTo = samtaleTo;
    }
}