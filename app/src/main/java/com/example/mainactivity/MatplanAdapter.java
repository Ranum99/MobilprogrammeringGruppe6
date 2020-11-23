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

public class MatplanAdapter extends RecyclerView.Adapter<MatplanAdapter.MatplanViewHolder> {

    private List<MatplanModel> Matplanlist;
    private LayoutInflater inflater;
    private Context context;
    private Database database;

    public MatplanAdapter(Context context, List<MatplanModel> MatplanList) {
        this.inflater = LayoutInflater.from(context);
        this.Matplanlist = MatplanList;
        this.context = context;
    }

    @NonNull
    @Override
    public MatplanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = inflater.inflate(R.layout.matplan_list_item, parent, false);
        return new MatplanViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatplanViewHolder viewHolder, int position) {
        MatplanModel matplanToDisplay = Matplanlist.get(position);

        viewHolder.setMatplan(matplanToDisplay, position);
        viewHolder.setDelete(matplanToDisplay, position);
        viewHolder.setMatplanen(matplanToDisplay, position);
    }

    @Override
    public int getItemCount() {
            return Matplanlist.size();
    }

    public class MatplanViewHolder extends RecyclerView.ViewHolder {

        // Cardviewet
        private CardView card;

        // Elementer i cardviewet
        private TextView uke;
        private ImageButton delete;

        // Variabler


        public MatplanViewHolder(@NonNull View itemView) {
            super(itemView);
            uke = itemView.findViewById(R.id.CardviewMatplan);
        }

        public void setMatplan(MatplanModel matplanToDisplay, int position) {
            uke = itemView.findViewById(R.id.CardviewMatplan);
            uke.setText("Matplan uke " + matplanToDisplay.getUkenr());

        }

        public void setDelete(final MatplanModel matplanToDisplay, final int position) {
            delete = itemView.findViewById(R.id.deletematplan);
            // pop up som spør om brukeren vil slette oppføringer
            View.OnClickListener deleteBursdag = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Slett bursdag")
                            .setMessage("Er du sikker på at du vil slette denne bursdagen?");
                    builder.setPositiveButton("Ja",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Sletter matplanen
                                    database = new Database(context);
                                    database.deleteRowFromTableById(Database.TABLE_MATPLAN ,matplanToDisplay.getId());
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

            delete.setOnClickListener(deleteBursdag);
        }

        //Fjerner og oppdaterer element fra recyclerviewet
        private void removeItem(int position) {
            Matplanlist.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, Matplanlist.size());
        }

        public void setMatplanen(final MatplanModel matplanToDisplay, int position) {
            card = itemView.findViewById(R.id.Matplancardview);
            View.OnClickListener edit = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ID", matplanToDisplay.getId());
                    bundle.putString("UKE", matplanToDisplay.getUkenr());

                    Navigation.findNavController(card).navigate(R.id.matplanListeFragment, bundle);
                }
            };
            card.setOnClickListener(edit);
        }
    }
}
