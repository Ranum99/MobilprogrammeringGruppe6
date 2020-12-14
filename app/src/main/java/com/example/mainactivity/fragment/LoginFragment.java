package com.example.mainactivity.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mainactivity.Database;
import com.example.mainactivity.MainActivity;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;
import com.google.android.material.snackbar.Snackbar;

public class LoginFragment extends Fragment {
    public LoginFragment() {}

    Database database;
    SharedPreferences sharedPreferences;
    private EditText email, password;
    private Button login, opprettBruker;
    private ImageView logo;
    int autoSave;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final NavController navController = Navigation.findNavController(view);

        database = new Database(getActivity());
        // User.SESSION is a unique variable to identify the instance of this shared preference
        sharedPreferences = this.requireActivity().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        int j = sharedPreferences.getInt("key",0);

        // Default is 0 so autologin is disabled
        if(j > 0) {
            Intent activity = new Intent(getContext(), MainActivity.class);
            startActivity(activity);
        }

        logo = view.findViewById(R.id.LoginLogo);
        logo.setImageResource(R.drawable.logo);

        opprettBruker = view.findViewById(R.id.OpprettBruker);
        opprettBruker.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_signupFragment));

        password = view.findViewById(R.id.PasswordLogin);
        email = view.findViewById(R.id.EmailLogin);
        email.setFilters(new InputFilter[] {
                new InputFilter.AllCaps() {
                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        return String.valueOf(source).toLowerCase().replace(" ", "");
                    }
                }
        });

        login = view.findViewById(R.id.Login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailen = email.getText().toString();
                String passordet = password.getText().toString();

                if (emailen.length() != 0 && passordet.length() != 0) {
                    if(!isNetworkAvailable()) {
                        Snackbar.make(view.findViewById(R.id.Login), "Du har ikke nett",
                                Snackbar.LENGTH_SHORT)
                                .show();
                                Log.e("LoginFragment", "Brukeren har ikke nettverkstilkobling");
                    }
                    else {
                        if (LoginUser(emailen, passordet)) {

                            email.setText("");
                            password.setText("");

                            if (sharedPreferences.getString(User.FAMILIE, null) == null)
                                Navigation.findNavController(login).navigate(R.id.action_loginFragment_to_familieFragment);
                            else {
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                } else
                    Toast.makeText(getActivity(), "Du må fylle ut alle feltene", Toast.LENGTH_SHORT).show();
                    Log.e("LoginFragment", "Brukeren fylte ikke ut alle feltene");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    public boolean LoginUser(String email, String password) {
        Cursor data = database.getData();

        while(data.moveToNext()) {

            if (data.getString(2).equals(email) && data.getString(5).equals(password) ) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(User.ID, data.getString(0));
                editor.putString(User.NAME, data.getString(1));
                editor.putString(User.EMAIL, data.getString(2));
                editor.putString(User.BIRTHDAY, data.getString(3));
                editor.putString(User.MOBILNR, data.getString(4));
                editor.putString(User.FAMILIE, data.getString(6));

                // Once the user clicks login, it will add 1 to sharedPreference which will allow autologin in OnViewCreated
                autoSave = 1;
                editor.putInt("key", autoSave);
                editor.apply();

                return true;
            }
        }
        Toast.makeText(getActivity(), "Ikke gyldig bruker", Toast.LENGTH_SHORT).show();
        Log.e("LoginFragment", "Brukeren er ikke gyldig");
        return false;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}