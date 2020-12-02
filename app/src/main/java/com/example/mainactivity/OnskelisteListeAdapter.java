package com.example.mainactivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OnskelisteListeAdapter extends RecyclerView.Adapter<OnskelisteListeAdapter.OnskelisteViewHolder>{

    private List<OnskelisteListeModel> WishListe;
    private LayoutInflater inflater;
    private ArrayList<ConstraintLayout> cards = new ArrayList<>();
    private OnskelisteListeModel WishesToDisplay;
    private Context contexten;
    private Database database;
    private SharedPreferences sharedPreferences;

    private int meID;

    public OnskelisteListeAdapter(Context context, List<OnskelisteListeModel> WishListe) {
        this.inflater = LayoutInflater.from(context);
        this.WishListe = WishListe;
        this.contexten = context;
    }


    private void removeItem(int position) {
        WishListe.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, WishListe.size());
    }


    @NonNull
    @Override
    public OnskelisteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {

        database = new Database(contexten);
        sharedPreferences = getContexten().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        View itemView = inflater.inflate(R.layout.onskeliste_list_item, parent, false);
        return new OnskelisteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OnskelisteListeAdapter.OnskelisteViewHolder viewHolder, int position) {

        WishesToDisplay = WishListe.get(position);

        viewHolder.setWish(WishesToDisplay);
        viewHolder.setDelete(WishesToDisplay, position);
        viewHolder.setCheckBox(WishesToDisplay);
        viewHolder.hideElements(WishesToDisplay);
    }

    @Override
    public int getItemCount() {
        return WishListe.size();
    }

    public Context getContexten() {
        return contexten;
    }

    public class OnskelisteViewHolder extends RecyclerView.ViewHolder {

        private TextView wish;
        private CheckBox checkBox;
        private CardView cardView;
        private ImageButton delete;

        public OnskelisteViewHolder(@NonNull final View itemView) {
            super(itemView);
        }


        public void setWish(final OnskelisteListeModel wishesToDisplay) {
            wish = itemView.findViewById(R.id.Onskelistenavn);

            String text = wishesToDisplay.getWish();

            wish.setText(text);
        }

        public void setDelete(final OnskelisteListeModel wishesToDisplay, final int position) {
            delete = itemView.findViewById(R.id.OnskelisteDelete);
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
                                    database.deleteRowFromTableById(Database.TABLE_WISH , String.valueOf(wishesToDisplay.getWishID()));
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
                }
            };

            delete.setOnClickListener(onClickListener);
        }

        public void setCheckBox(final OnskelisteListeModel wishesToDisplay) {
            checkBox = itemView.findViewById(R.id.checkBox);
            if (wishesToDisplay.getCheckBox())
                checkBox.setChecked(true);

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCheckBox(wishesToDisplay);
                }
            });
        }

        private void updateCheckBox(OnskelisteListeModel wishesToDisplay) {
            int isChecked;
            if (wishesToDisplay.getCheckBox())
                isChecked = 0;
            else
                isChecked = 1;

            boolean insertData = database.updateCheckBoxForWish(wishesToDisplay.getWishID(), isChecked);

            if (insertData)
                Toast.makeText(contexten, "Data successfully inserted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(contexten, "Something went wrong", Toast.LENGTH_SHORT).show();
        }

        public void hideElements(OnskelisteListeModel wishesToDisplay) {
            checkBox = itemView.findViewById(R.id.checkBox);
            delete = itemView.findViewById(R.id.OnskelisteDelete);

            if (wishesToDisplay.getUserID() != meID)
                delete.setVisibility(View.INVISIBLE);
            else
                checkBox.setVisibility(View.INVISIBLE);
        }
    }

}

