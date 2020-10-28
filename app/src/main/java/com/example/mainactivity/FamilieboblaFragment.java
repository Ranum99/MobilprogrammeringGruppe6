package com.example.mainactivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FamilieboblaFragment extends Fragment {
    public FamilieboblaFragment() {}
    private EditText search;
    FamilieboblaSamtaleFragment fs;
    Database database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_familiebobla, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setListenerOnBtns();

        search = view.findViewById(R.id.search);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showElements();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setListenerOnBtns() {
        ConstraintLayout elementWrapper = getActivity().findViewById(R.id.leggTilElementer);
        final int children = elementWrapper.getChildCount();

        for (int i = 0; i < children; i++) {
            Button btn = (Button) elementWrapper.getChildAt(i);

            //goToNewSiteListener(btn.getId(), FamilieboblaSamtale.class);
        }
    }

    private void showElements() {
        ConstraintLayout elementWraper = getActivity().findViewById(R.id.leggTilElementer);
        final int children = elementWraper.getChildCount();

        for (int i = 0; i < children; i++) {
            Button btn = (Button) elementWraper.getChildAt(i);
            String txtOfBtn = btn.getText().toString().toLowerCase();

            if (!txtOfBtn.contains(search.getText().toString().toLowerCase()))
                btn.setVisibility(View.GONE);
            else {
                btn.setVisibility(View.VISIBLE);

                //goToNewSiteListener(btn.getId(), FamilieboblaSamtale.class);
            }
        }
    }

}