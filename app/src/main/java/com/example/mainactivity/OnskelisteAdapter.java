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
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class OnskelisteAdapter extends RecyclerView.Adapter<OnskelisteAdapter.OnskelisteViewHolder> {
    private List<OnskelisteModel> Onskelister;
    private LayoutInflater inflater;
    private OnskelisteModel modelToDisplay;
    private Context contexten;
    private Database database;
    private SharedPreferences sharedPreferences;
    private int meID;

    public OnskelisteAdapter(Context context, List<OnskelisteModel> Onskelister, int meID) {
        this.inflater = LayoutInflater.from(context);
        this.Onskelister = Onskelister;
        this.contexten = context;
        this.meID = meID;
    }

    private void removeItem(int position) {
        Onskelister.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, Onskelister.size());
    }

    @NonNull
    @Override
    public OnskelisteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        sharedPreferences = contexten.getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        View itemView = inflater.inflate(R.layout.handleliste_list_item, parent, false);

        return new OnskelisteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OnskelisteViewHolder viewHolder, int position) {
        modelToDisplay = Onskelister.get(position);

        viewHolder.setOnskeliste(modelToDisplay);
        viewHolder.setDeleteOnOnskeliste(modelToDisplay, position);
        viewHolder.setClickOnOnskeliste(modelToDisplay);
        viewHolder.hideElements(modelToDisplay);
    }

    @Override
    public int getItemCount() {
        return Onskelister.size();
    }

    public class OnskelisteViewHolder extends RecyclerView.ViewHolder {
        private TextView tittel, navn;
        private CardView card;
        private ImageView delete;

        public OnskelisteViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setOnskeliste(OnskelisteModel modelToDisplay) {
            tittel = itemView.findViewById(R.id.handlelistenummer);
            navn = itemView.findViewById(R.id.bruker);
            tittel.setText(modelToDisplay.getWishlistName());
            navn.setText("Opprettet av: " + modelToDisplay.getUserToName());
        }

        public void setDeleteOnOnskeliste(final OnskelisteModel modelToDisplay, final int position) {
            delete = itemView.findViewById(R.id.handlelisteDelete);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexten);
                    builder.setTitle("Slett ønskelisten")
                            .setMessage("Er du sikker på at du vil slette denne ønskelisten?");
                    builder.setPositiveButton("Jepp, bare å slette",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Sletter samtalen
                                    database = new Database(contexten);
                                    database.deleteRowFromTableById(Database.TABLE_WISHLIST , String.valueOf(modelToDisplay.getWishlistID()));
                                    removeItem(position);
                                    System.out.println("Ønskelisten er slettet");
                                }
                            });
                    builder.setNegativeButton("NEI! Var bare en prank",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    System.out.println("Ønskelisten ble IKKE slettet");
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                    System.out.println(modelToDisplay.getWishlistID() + " - " + modelToDisplay.getWishlistName() + "(" + modelToDisplay.getUserToName() + ")");
                }
            };
            delete.setOnClickListener(onClickListener);
        }

        public void setClickOnOnskeliste(final OnskelisteModel modelToDisplay) {
            card = itemView.findViewById(R.id.cardHandleliste);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("onskelisteId", modelToDisplay.getWishlistID());
                    bundle.putString("onskelisteForBruker", modelToDisplay.getUserToName());
                    bundle.putInt("onskelisteForBrukerID", modelToDisplay.getUserToID());
                    bundle.putString("onskelisteNavn", modelToDisplay.getWishlistName());

                    Navigation.findNavController(card).navigate(R.id.action_onskelisteFragment_to_onskelisteListeFragment, bundle);
                }
            });
        }

        public void hideElements(OnskelisteModel modelToDisplay) {
            delete = itemView.findViewById(R.id.handlelisteDelete);

            if (modelToDisplay.getUserToID() != meID)
                delete.setVisibility(View.INVISIBLE);
        }
    }
}
