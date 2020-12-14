package com.example.mainactivity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.Database;
import com.example.mainactivity.model.HandlelisteModel;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;
import java.util.List;

public class HandlelisteAdapter extends RecyclerView.Adapter<HandlelisteAdapter.HandlelisteViewHolder> {

    private List<HandlelisteModel> Handlelister;
    private LayoutInflater inflater;
    private Context context;
    private Database database;
    private SharedPreferences sharedPreferences;
    private HandlelisteModel modelToDisplay;
    private int familieID;

    public HandlelisteAdapter(Context context, List<HandlelisteModel> HandlelisteListe, int iD) {
        this.inflater = LayoutInflater.from(context);
        this.Handlelister = HandlelisteListe;
        this.context = context;
        this.familieID = iD;
    }

    @NonNull
    @Override
    public HandlelisteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        sharedPreferences = context.getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        View itemView = inflater.inflate(R.layout.handleliste_list_item, parent, false);
        return new HandlelisteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HandlelisteViewHolder viewHolder, int position) {
        modelToDisplay = Handlelister.get(position);

        viewHolder.setHandleliste(modelToDisplay, position);
        viewHolder.setDelete(modelToDisplay, position);
        viewHolder.setEdit(modelToDisplay, position);
    }

    @Override
    public int getItemCount() {
        return Handlelister.size();
    }

    // Indre klasse
    public class HandlelisteViewHolder extends RecyclerView.ViewHolder {

        //Cardviewet
        private CardView card;
        private TextView nr, bruker;
        private ImageView delete;

        public HandlelisteViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setHandleliste(HandlelisteModel modelToDisplay, int position) {
            // Kobler variablene med sine respektive elementer i cardviewet
            nr = itemView.findViewById(R.id.handlelistenummer);
            bruker = itemView.findViewById(R.id.bruker);
            // setter teksten i cardviewet
            nr.setText(modelToDisplay.getTittel());
            bruker.setText("Opprettet av: " + modelToDisplay.getNavn());
        }

        // Gjør at en bruker kan slette handlelisten
        public void setDelete(final HandlelisteModel modelToDisplay, final int position) {
            delete = itemView.findViewById(R.id.handlelisteDelete);
            // pop up som spør om brukeren vil slette oppføringer
            View.OnClickListener deleteHandleliste = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Slett handleliste")
                            .setMessage("Er du sikker på at du vil slette denne handlelisten?");
                    builder.setPositiveButton("Ja",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Sletter bursdagen
                                    database = new Database(context);
                                    database.deleteRowFromTableById(Database.TABLE_HANDLELISTE, modelToDisplay.getId());
                                    Log.i("HandlelisteAdapter", "Bursdagen ble slettet");
                                    removeItem(position);
                                }
                            });
                    builder.setNegativeButton("NEI!",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Går ut av popup'en og tilbake til siden uten å gjøre noe
                                    Log.i("HandlelisteAdapter", "Bursdagen ble ikke slettet");
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                }
            };
            delete.setOnClickListener(deleteHandleliste);
        }

        //Fjerner og oppdaterer element fra recyclerviewet
        private void removeItem(int position) {
            Handlelister.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, Handlelister.size());
        }

        // Dersom trykker på CardView, bli han sendt videre handlelisten med en bundle
        public void setEdit(final HandlelisteModel modelToDisplay, int position) {
            card = itemView.findViewById(R.id.cardHandleliste);
            View.OnClickListener edit = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    bundle.putString("TITTEL", modelToDisplay.getTittel());
                    bundle.putString("ID", modelToDisplay.getId());
                    bundle.putInt("FAMILIEID", modelToDisplay.getFamilieID());

                    Navigation.findNavController(card).navigate(R.id.handlelisteListeFragment, bundle);
                }
            };
            card.setOnClickListener(edit);
        }
    }
}