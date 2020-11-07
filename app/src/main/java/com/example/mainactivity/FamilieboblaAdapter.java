package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FamilieboblaAdapter extends RecyclerView.Adapter<FamilieboblaAdapter.FamilieboblaViewHolder>{

    private List<FamilieboblaModel> SamtaleListe;
    private LayoutInflater inflater;
    private ArrayList<ConstraintLayout> cards = new ArrayList<>();
    private FamilieboblaModel SamtaleToDisplay;
    private Context contexten;
    private Database database;

    public FamilieboblaAdapter(Context context, List<FamilieboblaModel> SamtaleListe) {
        this.inflater = LayoutInflater.from(context);
        this.SamtaleListe = SamtaleListe;
        this.contexten = context;
    }

    @NonNull
    @Override
    public FamilieboblaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = inflater.inflate(R.layout.familiebobla_list_item, parent, false);
        
        return new FamilieboblaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilieboblaAdapter.FamilieboblaViewHolder viewHolder, int position) {
        SamtaleToDisplay = SamtaleListe.get(position);

        viewHolder.setSamtale(SamtaleToDisplay);
        viewHolder.setDeleteOnSamtale(SamtaleToDisplay);
    }

    @Override
    public int getItemCount() {
        return SamtaleListe.size();
    }

    public class FamilieboblaViewHolder extends RecyclerView.ViewHolder {

        private TextView navn, dato, mobil, aar;
        private ConstraintLayout card;
        private ImageButton delete;

        public FamilieboblaViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.cardID);
            card.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_familieboblaFragment_to_familieboblaSamtaleFragment));


            delete = itemView.findViewById(R.id.imageButton);

        }

        public void setSamtale(final FamilieboblaModel SamtaleToDisplay) {
            navn = itemView.findViewById(R.id.FamilieBoblaNameCardview);
            navn.setText(SamtaleToDisplay.getNavn());
        }

        public void setDeleteOnSamtale(final FamilieboblaModel SamtaleToDisplay) {
            delete = itemView.findViewById(R.id.imageButton);
            View.OnClickListener nene = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexten);
                    builder.setTitle("Slett samtale")
                            .setMessage("Er du sikker på at du vil slette denne samtalen med " + getNavn() + "?");
                    builder.setPositiveButton("Jepp, bare å slette",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Sletter samtalen
                                    database = new Database(contexten);
                                    database.deleteConversation(SamtaleToDisplay.getIden());
                                    System.out.println("Samtalen er slettet");
                                }
                            });
                    builder.setNegativeButton("NEI! Var bare en prank",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    System.out.println("Samtalen ble IKKE slettet");
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                    System.out.println(SamtaleToDisplay.getIden() + " - " + getNavn());
                }
            };

            delete.setOnClickListener(nene);
        }

        public String getNavn() {
            return navn.getText().toString();
        }
    }

}
