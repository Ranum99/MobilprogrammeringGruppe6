package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class GruppeinformasjonFragment extends Fragment {

    public GruppeinformasjonFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gruppeinformasjon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton tilbake = view.findViewById(R.id.GruppeinfoTilbake);
        tilbake.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_gruppeinformasjonFragment_to_hovedsideFragment));

    }
}