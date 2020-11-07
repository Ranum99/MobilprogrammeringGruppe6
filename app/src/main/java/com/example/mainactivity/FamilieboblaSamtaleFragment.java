package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class FamilieboblaSamtaleFragment extends Fragment {

    public FamilieboblaSamtaleFragment() {}

    private int samtaleId;
    private String samtaleName;
    private FamilieboblaAdapter familieboblaAdapter;

    private TextView samtaleTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_familiebobla_samtale, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        samtaleId = Integer.parseInt(getArguments().getString("samtaleId"));
        samtaleName = getArguments().getString("samtaleName");

        samtaleTitle = view.findViewById(R.id.SamtaleBrukernavn);
        samtaleTitle.setText(samtaleName);

        setConversation(samtaleId);
    }

    private void setConversation(int samtaleId) {

    }

    public void setSamtaleId(int samtaleId) {
        this.samtaleId = samtaleId;
    }

    public void setSamtaleName(FamilieboblaModel SamtaleToDisplay) {
        this.samtaleName = SamtaleToDisplay.getNavn();
    }
}