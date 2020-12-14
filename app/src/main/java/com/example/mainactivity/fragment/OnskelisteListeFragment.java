package com.example.mainactivity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainactivity.Database;
import com.example.mainactivity.R;
import com.example.mainactivity.adapter.OnskelisteListeAdapter;
import com.example.mainactivity.model.OnskelisteListeModel;
import com.example.mainactivity.model.User;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;

public class OnskelisteListeFragment extends Fragment {
    public OnskelisteListeFragment() {
        // Required empty constructor
    }

    Database database;
    SharedPreferences sharedPreferences;

    private int wishlistID, wishlistForUserID;
    private String wishlistName, wishlistForUserName;
    private ArrayList<OnskelisteListeModel> wishes;
    private TextView onskelisteBruker;
    private EditText onskeToAdd;
    private Button leggTilBtn;
    private TextInputLayout onskeToAddParent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onskeliste_liste, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        database = new Database(getActivity());
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        wishlistID = getArguments().getInt("onskelisteId");
        wishlistForUserID = getArguments().getInt("onskelisteForBrukerID");
        wishlistName = getArguments().getString("onskelisteNavn");
        wishlistForUserName = getArguments().getString("onskelisteForBruker");

        String text = wishlistName + " (" + wishlistForUserName + ")";
        onskelisteBruker = view.findViewById(R.id.OnskelisteBruker);
        onskelisteBruker.setText(text);

        onskeToAddParent = view.findViewById(R.id.textInputLayout);
        onskeToAdd = view.findViewById(R.id.onskeToAdd);
        leggTilBtn = view.findViewById(R.id.leggTil);
        leggTilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWish();
            }
        });

        setWishesInWishlist();
        setUpRecyclerView();
        if (wishlistForUserID != Integer.parseInt(sharedPreferences.getString(User.ID, null)))
            hideElements();
    }

    private void hideElements() {
        onskeToAddParent.setVisibility(View.INVISIBLE);
        leggTilBtn.setVisibility(View.INVISIBLE);
    }

    private void setUpRecyclerView() {
        RecyclerView onskelisteListeRecyclerView = getView().findViewById(R.id.OnskelisteRecyclerview);
        onskelisteListeRecyclerView.setAdapter(new OnskelisteListeAdapter(getContext(), wishes));

        onskelisteListeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setWishesInWishlist() {
        // Getting data from database table MESSAGES
        Cursor wishes = database.getAllWishesFromWishlist(wishlistID);

        ArrayList<OnskelisteListeModel> allWishes = new ArrayList<>();

        while(wishes.moveToNext()) {
            int wishID = Integer.parseInt(wishes.getString(wishes.getColumnIndex(Database.COLUMN_ID)));
            int wishlistID = wishes.getInt(wishes.getColumnIndex(Database.COLUMN__WISHLIST_ID));
            String wish = wishes.getString(wishes.getColumnIndex(Database.COLUMN__NAME_WISH));
            boolean isChecked = wishes.getInt(wishes.getColumnIndex(Database.COLUMN__WISH_CHECKED)) == 1;
            int userID = wishes.getInt(wishes.getColumnIndex(Database.COLUMN__WISH_USER_ID));

            if (wishlistID == this.wishlistID) {
                OnskelisteListeModel wish1 = new OnskelisteListeModel(wishID, userID, wish, isChecked);

                allWishes.add(wish1);
            }
        }
        this.wishes = allWishes;
    }

    private void addWish() {
        int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));
        long addToDatabase = -1;

        if (!onskeToAdd.getText().toString().isEmpty())
            addToDatabase = database.addWishToWishlist(wishlistID, onskeToAdd.getText().toString(), meID);
        else
            Toast.makeText(getContext(),"Du må skrive inn et ønske først", Toast.LENGTH_SHORT).show();

        if (addToDatabase >= 0) {
            Toast.makeText(getContext(),"Added wish: " + onskeToAdd.getText().toString(), Toast.LENGTH_SHORT).show();
            onskeToAdd.setText("");
            setWishesInWishlist();
            setUpRecyclerView();
        }
    }
}