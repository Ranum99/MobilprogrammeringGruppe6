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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.BirthdayViewHolder>{

    private List<BirthdayModel> BirthdayList;
    private LayoutInflater inflater;
    private Context context;
    private Database database;

    public BirthdayAdapter(Context context, List<BirthdayModel> BirthdayList) {
        this.inflater = LayoutInflater.from(context);
        this.BirthdayList = BirthdayList;
        this.context = context;
    }

    private void removeItem(int position) {
        BirthdayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, BirthdayList.size());
    }

    @NonNull
    @Override
    public BirthdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = inflater.inflate(R.layout.birthday_list_item, parent, false);
        return new BirthdayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayViewHolder viewHolder, int position) {
        BirthdayModel birthdayToDisplay = BirthdayList.get(position);

        viewHolder.setBirthday(birthdayToDisplay, position);
        viewHolder.setDelete(birthdayToDisplay, position);
        //viewHolder.setOnClickBursdag(birthdayToDisplay, position);
    }

    @Override
    public int getItemCount() {
        return BirthdayList.size();
    }


    public class BirthdayViewHolder extends RecyclerView.ViewHolder {

        private TextView navn, dato, mobil, aar;
        private ConstraintLayout card;
        private ImageButton delete;

        public BirthdayViewHolder(@NonNull View itemView) {
            super(itemView);

            navn = itemView.findViewById(R.id.CardviewFullName);
            dato = itemView.findViewById(R.id.CardviewDate);
            mobil = itemView.findViewById(R.id.CardviewPhone);
            aar = itemView.findViewById(R.id.CardviewAge);
            delete = itemView.findViewById(R.id.deleteBursdag);
        }

        public void setBirthday(BirthdayModel birthdayToDisplay, int position) {
            navn.setText(birthdayToDisplay.getNavn());
            dato.setText(birthdayToDisplay.getDato());
            mobil.setText(birthdayToDisplay.getMobil());
        }

        public void setDelete(final BirthdayModel birthdayToDisplay, final int position) {

            View.OnClickListener deleteBursdag = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Slett bursdag")
                            .setMessage("Er du sikker på at du vil slette denne bursdagen");
                    builder.setPositiveButton("Ja",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Sletter samtalen
                                    database = new Database(context);
                                    database.deleteRowFromTableById(Database.TABLE_BIRTHDAY ,birthdayToDisplay.getNavn());
                                    removeItem(position);
                                }
                            });
                    builder.setNegativeButton("NEI!",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.show();

                }
            };

            delete.setOnClickListener(deleteBursdag);
        }

        /*public void setOnClickBursdag(final BirthdayModel birthdayToDisplay, int position) {
            card = itemView.findViewById(R.id.cardBursdag);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("Navn", birthdayToDisplay.getNavn());
                    bundle.putString("Dato", birthdayToDisplay.getDato());
                    bundle.putString("mobil", birthdayToDisplay.getMobil());


                    Navigation.findNavController(card).navigate(R.id.bursdagLeggTilFragment, bundle);
                }
            });
        }

         */
    }

}
