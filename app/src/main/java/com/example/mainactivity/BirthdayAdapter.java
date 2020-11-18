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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Array;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
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

    @NonNull
    @Override
    public BirthdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = inflater.inflate(R.layout.birthday_list_item, parent, false);
        return new BirthdayViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull BirthdayViewHolder viewHolder, int position) {
        BirthdayModel birthdayToDisplay = BirthdayList.get(position);

        viewHolder.setBirthday(birthdayToDisplay, position);
        viewHolder.setDelete(birthdayToDisplay, position);
    }

    @Override
    public int getItemCount() {
        return BirthdayList.size();
    }

    // Inner klasse
    public class BirthdayViewHolder extends RecyclerView.ViewHolder {

        //Elementer i cardviewet
        private TextView navn, dato, aar;
        private ImageButton delete;
        // Variabler
        private String id;
        private Integer splitAar, splitMaaned, splitDag;
        private LocalDate today, birthday;
        private Period period;


        public BirthdayViewHolder(@NonNull View itemView) {
            super(itemView);

            //Kobler variablene med sine respektive elementer i cardviewet
            navn = itemView.findViewById(R.id.CardviewFullName);
            dato = itemView.findViewById(R.id.CardviewDate);
            aar = itemView.findViewById(R.id.CardviewAge);
            delete = itemView.findViewById(R.id.deleteBursdag);

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void setBirthday(BirthdayModel birthdayToDisplay, int position) {

            // Regner ut personens alder
            String datoinput = birthdayToDisplay.getDato();
            String[] parts = datoinput.split("\\.");

            splitAar = Integer.parseInt(parts[2]);
            splitMaaned = Integer.parseInt(parts[1]);
            splitDag = Integer.parseInt(parts[0]);

            today = LocalDate.now();
            //birthday = LocalDate.of(splitAar, splitMaaned, splitDag);
            //period = Period.between(birthday, today);

            // Setter texten i cardviewet
            navn.setText(birthdayToDisplay.getNavn());
            dato.setText("Født: " + birthdayToDisplay.getDato());
            //aar.setText("Fyller " + String.valueOf(period.getYears()+1) + " år");
            aar.setText("x");
            id = birthdayToDisplay.getId();
        }

        //Fjerner og oppdaterer element fra recyclerviewet
        private void removeItem(int position) {
            BirthdayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, BirthdayList.size());
        }

        public void setDelete(final BirthdayModel birthdayToDisplay, final int position) {

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
                                    // Sletter bursdagen
                                    database = new Database(context);
                                    database.deleteRowFromTableById(Database.TABLE_BIRTHDAY ,birthdayToDisplay.getId());
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


    }

}
