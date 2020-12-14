package com.example.mainactivity.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mainactivity.Database;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;

public class OnskelisteLeggTilFragment extends Fragment {
    public OnskelisteLeggTilFragment() {}

    SharedPreferences sharedPreferences;
    Database database;

    private NavController navController;
    private Button lagListe;
    private EditText wishlistName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_onskeliste_legg_til, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        lagListe = view.findViewById(R.id.lagOnskeliste);
        wishlistName = view.findViewById(R.id.wishlistName);

        lagListe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeNewWishlist();
            }
        });
    }

    private void makeNewWishlist() {
        int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));
        long addToDatabase = -1;

        if (!wishlistName.getText().toString().isEmpty())
            addToDatabase = database.makeNewWishlist(meID, wishlistName.getText().toString());
        else
            Toast.makeText(getActivity(), "Du må sette et navn på ønskelisten", Toast.LENGTH_SHORT).show();

        if (addToDatabase >= 0) {
            Toast.makeText(getContext(),"Made wishlist for: " + sharedPreferences.getString(User.NAME, null) + ", named: " + wishlistName.getText(), Toast.LENGTH_SHORT).show();
            System.out.println("Made wishlist for: " + sharedPreferences.getString(User.NAME, null) + ", named: " + wishlistName.getText());
            navController.navigateUp();
        }
    }
}