package com.example.mainactivity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.Database;
import com.example.mainactivity.model.OnskelisteListeModel;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;
import java.util.List;
import java.util.Objects;

public class OnskelisteListeAdapter extends RecyclerView.Adapter<OnskelisteListeAdapter.OnskelisteViewHolder>{
    private List<OnskelisteListeModel> WishListe;
    private LayoutInflater inflater;
    private Context contexten;
    private Database database;
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @NonNull
    @Override
    public OnskelisteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        SharedPreferences sharedPreferences = getContexten().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        database = new Database(contexten);
        meID = Integer.parseInt(Objects.requireNonNull(sharedPreferences.getString(User.ID, null)));

        View itemView = inflater.inflate(R.layout.onskeliste_list_item, parent, false);
        return new OnskelisteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OnskelisteListeAdapter.OnskelisteViewHolder viewHolder, int position) {
        OnskelisteListeModel wishesToDisplay = WishListe.get(position);

        viewHolder.setWish(wishesToDisplay);
        viewHolder.setDelete(wishesToDisplay, position);
        viewHolder.setCheckBox(wishesToDisplay);
        viewHolder.hideElements(wishesToDisplay);
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
        private ImageView delete;

        public OnskelisteViewHolder(@NonNull final View itemView) {
            super(itemView);
        }


        public void setWish(final OnskelisteListeModel wishesToDisplay) {
            wish = itemView.findViewById(R.id.Onskelistenavn);

            String text = wishesToDisplay.getWish();

            wish.setText(text);
        }

        // Gjør at en bruker kan slette et ønske
        public void setDelete(final OnskelisteListeModel wishesToDisplay, final int position) {
            delete = itemView.findViewById(R.id.OnskelisteDelete);
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexten);
                    builder.setTitle("Slett ønske")
                            .setMessage("Er du sikker på at du vil slette dette ønsket??");
                    builder.setPositiveButton("Ja",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Sletter samtalen
                                    database = new Database(contexten);
                                    database.deleteRowFromTableById(Database.TABLE_WISH , String.valueOf(wishesToDisplay.getWishID()));
                                    removeItem(position);
                                    Log.i("OnskelisteListeAdapter", "Ønske er slettet");
                                }
                            });
                    builder.setNegativeButton("Nei",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Log.i("OnskelisteListeAdapter", "Ønske ble ikke slettet");
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                }
            };
            delete.setOnClickListener(onClickListener);
        }

        // Setter checkbox til omvendt av hva den er
        public void setCheckBox(final OnskelisteListeModel wishesToDisplay) {
            checkBox = itemView.findViewById(R.id.checkBox);
            checkBox.setChecked(!checkBox.isChecked());

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateCheckBox(wishesToDisplay);
                }
            });
        }

        // Opdaterer om ønske i databasen
        private void updateCheckBox(OnskelisteListeModel wishesToDisplay) {
            int isChecked;
            if (wishesToDisplay.getCheckBox())
                isChecked = 0;
            else
                isChecked = 1;

            boolean insertData = database.updateCheckBoxForWish(wishesToDisplay.getWishID(), isChecked);

            if (insertData)
                Log.i("OnskelisteListeAdapter", "Data successfully inserted");
            else
                Log.e("OnskelisteListeAdapter", "Something went wrong");
        }

        // Gjemmer elementer er en bruker som ikke eier ønskelisten ikke skal se
        public void hideElements(OnskelisteListeModel wishesToDisplay) {
            checkBox = itemView.findViewById(R.id.checkBox);
            delete = itemView.findViewById(R.id.OnskelisteDelete);
            wish = itemView.findViewById(R.id.Onskelistenavn);

            if (wishesToDisplay.getUserID() != meID)
                delete.setVisibility(View.INVISIBLE);
            else {
                checkBox.setVisibility(View.INVISIBLE);

                ConstraintLayout constraintLayout = itemView.findViewById(R.id.OnskelisteCardID);
                ConstraintSet constraintSet = new ConstraintSet();
                constraintSet.clone(constraintLayout);
                constraintSet.connect(R.id.Onskelistenavn, ConstraintSet.START, R.id.OnskelisteCardID, ConstraintSet.START, 42);
                constraintSet.applyTo(constraintLayout);
            }
        }
    }
}

