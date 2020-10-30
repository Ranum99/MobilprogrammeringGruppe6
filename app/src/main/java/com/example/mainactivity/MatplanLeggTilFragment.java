package com.example.mainactivity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MatplanLeggTilFragment extends Fragment {

    public MatplanLeggTilFragment() {}

    Integer[] AntallDager = {1,2,3,4,5,6,7};
    String[] UkeDager = {"Mandag", "Tirsdag", "Onsdag", "Torsdag", "Fredag", "Lørdag", "Søndag"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_matplan_legg_til, container, false);
    }
}