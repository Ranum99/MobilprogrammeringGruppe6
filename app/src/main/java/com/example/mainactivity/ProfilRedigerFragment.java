package com.example.mainactivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Objects;

public class ProfilRedigerFragment extends Fragment {

    Database database;
    SharedPreferences sharedPreferences;

    private EditText endreNavn, endreEmail, endreMobilnr;
    private DatePicker endreBursdag;
    private String navn, mobil, email, dato, id;
    private Button btnChangePicture;
    // PICK_PHOTO_CODE is a constant integer
    public final static int PICK_PHOTO_CODE = 1046;

    public ProfilRedigerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profil_rediger, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this.requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        final NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment);

        // Instansierer variabler
        Button send = view.findViewById(R.id.EndreProfilRedigerBtn);
        endreNavn = view.findViewById(R.id.endreProfilNavn);
        endreBursdag = view.findViewById(R.id.endreProfilBursdag);
        endreEmail = view.findViewById(R.id.endreProfilEmail);
        endreMobilnr = view.findViewById(R.id.profilMobilnr);
        database = new Database(getActivity());
        sharedPreferences = requireContext().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        btnChangePicture = view.findViewById(R.id.btnChangePicture);

        assert getArguments() != null;
        endreNavn.setText(getArguments().getString("NAVN"));
        endreEmail.setText(getArguments().getString("EMAIL"));
        endreMobilnr.setText(getArguments().getString("MOBILNUMMER"));

        String[] parts = Objects.requireNonNull(requireArguments().getString("FODSELSDATO")).split("\\.");
        int dag, maaned, aar;
        dag = Integer.parseInt(parts[0]);
        maaned = Integer.parseInt(parts[1]);
        aar = Integer.parseInt(parts[2]);

        endreBursdag.updateDate(aar, maaned-1, dag);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navn = endreNavn.getText().toString();
                mobil = endreMobilnr.getText().toString();
                email = endreEmail.getText().toString();
                dato = endreBursdag.getDayOfMonth() + "." + (endreBursdag.getMonth()+1) + "." + endreBursdag.getYear();
                assert getArguments() != null;
                id = getArguments().getString("ID");
                database.updateUserInDatabase(id, navn, email, dato, mobil);
                System.out.println(dato);
                database.updateOneColumnFromTable(Database.TABLE_BIRTHDAY, Database.COLUMN_BIRTHDAY_DATE, dato, Database.COLUMN_BIRTHDAY_USERID, sharedPreferences.getString(User.ID, null));
                navController.navigateUp();
            }
        });

        btnChangePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                onPickPhoto(view1);
            }
        });
    }

    // Trigger gallery selection for a photo
    public void onPickPhoto(View view) {
        // Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Bring up gallery to select a photo
            startActivityForResult(intent, PICK_PHOTO_CODE);
        }
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            // check version of Android on device
            if(Build.VERSION.SDK_INT > 27){
                // on newer versions of Android, use the new decodeBitmap method
                ImageDecoder.Source source = ImageDecoder.createSource(this.requireActivity().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                // support older versions of Android by using getBitmap
                image = MediaStore.Images.Media.getBitmap(this.requireActivity().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((data != null) && requestCode == PICK_PHOTO_CODE) {
            Uri photoUri = data.getData();

            // Load the image located at photoUri into selectedImage
            Bitmap selectedImage = loadFromUri(photoUri);

            // Load the selected image into a preview
            ImageView ivPreview = (ImageView) requireView().findViewById(R.id.profilbildeRediger);
            ivPreview.setImageBitmap(selectedImage);
        }
    }
}
