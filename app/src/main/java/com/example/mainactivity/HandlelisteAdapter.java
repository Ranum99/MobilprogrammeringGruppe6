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
import androidx.cardview.widget.CardView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HandlelisteAdapter extends RecyclerView.Adapter<HandlelisteAdapter.HandlelisteViewHolder> {

    private List<HandlelisteModel> HandlelisteListe;
    private LayoutInflater inflater;
    private Context context;
    private Database database;

    public HandlelisteAdapter(Context context, List<HandlelisteModel> HandlelisteListe) {
        this.inflater = LayoutInflater.from(context);
        this.HandlelisteListe = HandlelisteListe;
        this.context = context;
    }

    @NonNull
    @Override
    public HandlelisteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = inflater.inflate(R.layout.handleliste_list_item, parent, false);
        return new HandlelisteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HandlelisteViewHolder viewHolder, int position) {
        HandlelisteModel modelToDisplay = HandlelisteListe.get(position);

        viewHolder.setHandleliste(modelToDisplay, position);
        viewHolder.setDelete(modelToDisplay, position);
        viewHolder.setEdit(modelToDisplay, position);

        viewHolder.setHandleliste(modelToDisplay, position);
        viewHolder.setDelete(modelToDisplay, position);
    }

    @Override
    public int getItemCount() {
        return HandlelisteListe.size();
    }

    private void removeItem(int position) {
        HandlelisteListe.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, HandlelisteListe.size());
    }

    // Indre klasse
    public class HandlelisteViewHolder extends RecyclerView.ViewHolder {

        //Cardviewet
        private CardView card;

        //Elementer i cardviewet
        private TextView nr;
        private ImageButton delete;

        //Variabler
        private int position;

        public HandlelisteViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setHandleliste(HandlelisteModel modelToDisplay, int position) {

            // Kobler variablene med sine respektive elementer i cardviewet
            nr = itemView.findViewById(R.id.handlelistenummer);
            delete = itemView.findViewById(R.id.handlelisteDelete);
            card = itemView.findViewById(R.id.cardHandleliste);

            // setter teksten i cardviewet
            nr.setText("Handleliste nr: " + modelToDisplay.getNr());

        }

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
                                    database.deleteRowFromTableById(Database.TABLE_HANDLELISTE ,modelToDisplay.getNr());
                                    removeItem(position);
                                }
                            });
                    builder.setNegativeButton("NEI!",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Går ut av popup'en og tilbake til siden uten å gjøre noe
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
            HandlelisteListe.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, HandlelisteListe.size());
        }

        public void setEdit(final HandlelisteModel modelToDisplay, int position) {
            card = itemView.findViewById(R.id.element);
            View.OnClickListener edit = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("UKENR", modelToDisplay.getNr());
                    bundle.putString("ID", modelToDisplay.getId());

                    Navigation.findNavController(card).navigate(R.id.handlelisteListeFragment, bundle);
                }
            };
            card.setOnClickListener(edit);
        }
    }
}