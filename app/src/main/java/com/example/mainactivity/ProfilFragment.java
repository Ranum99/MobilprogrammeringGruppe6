package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class ProfilFragment extends Fragment {
    SharedPreferences sharedPreferences;
    Button Logout;
    private TextView userID;

    public ProfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Endre profil
        Button endreProfilBtn = view.findViewById(R.id.btnEndreProfil);
        endreProfilBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_profilFragment_to_profilRedigerFragment));

        sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, MODE_PRIVATE);
        userID = view.findViewById(R.id.ProfilBrukerId);
        userID.setText(userID.getText() + sharedPreferences.getString(User.ID, null));

        //Logger ut bruker
        //Logout = view.findViewById(R.id.btnLoggUt);

        Intent intent = requireActivity().getIntent();
        String string = intent.getStringExtra("message");
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Confirmation Popup!").
                        setMessage("Are you sure that you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // Tømmer sharedPreferences
                                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.apply();
                                requireActivity().finish();

                                Intent intent1 = new Intent(requireActivity().getApplicationContext(), LoginFragment.class);
                                startActivity(intent1);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });
*/
    }
}