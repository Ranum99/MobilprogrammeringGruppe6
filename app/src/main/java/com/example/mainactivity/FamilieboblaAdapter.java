package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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


    private void removeItem(int position) {
        SamtaleListe.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, SamtaleListe.size());
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
        viewHolder.setDeleteOnSamtale(SamtaleToDisplay, position);
        viewHolder.setClickOnSamtale(SamtaleToDisplay);
    }

    @Override
    public int getItemCount() {
        return SamtaleListe.size();
    }


    public class FamilieboblaViewHolder extends RecyclerView.ViewHolder {

        private TextView navn;
        private ConstraintLayout card;
        private ImageButton delete;

        public FamilieboblaViewHolder(@NonNull final View itemView) {
            super(itemView);

        }


        public void setSamtale(final FamilieboblaModel SamtaleToDisplay) {
            navn = itemView.findViewById(R.id.FamilieBoblaNameCardview);

            String text = SamtaleToDisplay.getSamtaleName() + " (" + SamtaleToDisplay.getNavn() + ")";

            navn.setText(text);
        }

        public void setDeleteOnSamtale(final FamilieboblaModel SamtaleToDisplay, final int position) {
            delete = itemView.findViewById(R.id.imageButton);
            View.OnClickListener nene = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexten);
                    builder.setTitle("Slett samtale")
                            .setMessage("Er du sikker på at du vil slette denne samtalen med " + SamtaleToDisplay.getNavn() + "?");
                    builder.setPositiveButton("Jepp, bare å slette",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Sletter samtalen
                                    database = new Database(contexten);
                                    database.deleteRowFromTableById(Database.TABLE_CONVERSATION ,SamtaleToDisplay.getIden());
                                    removeItem(position);
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

        public void setClickOnSamtale(final FamilieboblaModel SamtaleToDisplay) {
            card = itemView.findViewById(R.id.cardID);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("samtaleId", SamtaleToDisplay.getIden());
                    bundle.putString("samtaleTo", SamtaleToDisplay.getNavn());
                    bundle.putString("samtaleName", SamtaleToDisplay.getSamtaleName());


                    Navigation.findNavController(card).navigate(R.id.action_familieboblaFragment_to_familieboblaSamtaleFragment, bundle);
                    System.out.println("meme");
                }
            });
        }

        public String getNavn() {
            return navn.getText().toString();
        }
    }

}
