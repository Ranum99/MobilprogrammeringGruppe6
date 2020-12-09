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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KalenderSideAdapter extends RecyclerView.Adapter<KalenderSideAdapter.KalenderViewHolder>{

    private List<KalenderSideModel> AktivitetsListe;
    private LayoutInflater inflater;
    private KalenderSideModel kalenderSideModel;
    private Context contexten;
    private Database database;
    private SharedPreferences sharedPreferences;

    private int meID;

    public KalenderSideAdapter(Context context, List<KalenderSideModel> AktivitetsListe) {
        this.inflater = LayoutInflater.from(context);
        this.AktivitetsListe = AktivitetsListe;
        this.contexten = context;
    }


    private void removeItem(int position) {
        AktivitetsListe.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, AktivitetsListe.size());
    }

    @NonNull
    @Override
    public KalenderSideAdapter.KalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        sharedPreferences = getContexten().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        View itemView = inflater.inflate(R.layout.kalender_aktivitet_list_item, parent, false);
        return new KalenderSideAdapter.KalenderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KalenderSideAdapter.KalenderViewHolder viewHolder, int position) {
        kalenderSideModel = AktivitetsListe.get(position);

        viewHolder.setAktivitet(kalenderSideModel);
        viewHolder.setDato(kalenderSideModel);
        viewHolder.hideDelete(kalenderSideModel, position);
    }

    @Override
    public int getItemCount() {
        return AktivitetsListe.size();
    }

    public Context getContexten() {
        return contexten;
    }

    public class KalenderViewHolder extends RecyclerView.ViewHolder {

        private TextView aktivitet, userName, datoOgTid;
        private ImageButton delete;
        private ConstraintLayout kortID;

        public KalenderViewHolder(@NonNull final View itemView) {
            super(itemView);

        }

        // Setter aktivitet og brukernavn på CardView
        public void setAktivitet(final KalenderSideModel ActivityToDisplay) {
            aktivitet = itemView.findViewById(R.id.aktivitet);
            userName = itemView.findViewById(R.id.brukerNavn);

            aktivitet.setText(ActivityToDisplay.getTheActivity());
            userName.setText("Aktivitet for: " + ActivityToDisplay.getUserName());
        }

        // Bygger opp en tekst og setter dato på CardView
        public void setDato(KalenderSideModel kalenderSideModel) {
            datoOgTid = itemView.findViewById(R.id.datoOgTid);

            String datoOfTidText = kalenderSideModel.getDateFrom();

            if (kalenderSideModel.getTimeFrom() != null)
                datoOfTidText += " (" + kalenderSideModel.getTimeFrom() + ")";

            if (kalenderSideModel.getDateTo() != null)
                datoOfTidText += " - " + kalenderSideModel.getDateTo();

            if (kalenderSideModel.getTimeTo() != null) {
                if (kalenderSideModel.getDateTo() == null)
                    datoOfTidText += " -";
                datoOfTidText += " (" + kalenderSideModel.getTimeTo() + ")";
            }

            datoOgTid.setText(datoOfTidText);
        }

        // Sletter en aktivitet
        public void setDeleteOnActivity(final KalenderSideModel kalenderSideModel, final int position) {
                delete = itemView.findViewById(R.id.slettAktivitet);
                View.OnClickListener onClickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(contexten);
                        builder.setTitle("Slett aktivitet")
                                .setMessage("Er du sikker på at du vil slette denne aktiviteten med?");
                        builder.setPositiveButton("Jepp, bare å slette",
                                new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        // Sletter aktivitet
                                        database = new Database(contexten);
                                        database.deleteRowFromTableById(Database.TABLE_CALENDAR_ACTIVITY , String.valueOf(kalenderSideModel.getActivityID()));
                                        removeItem(position);
                                        System.out.println("Aktiviteten er slettet");
                                    }
                                });
                        builder.setNegativeButton("NEI! Var bare en prank",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        System.out.println("Aktiviteten ble IKKE slettet");
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alert1 = builder.create();
                        alert1.show();
                    }
                };

                delete.setOnClickListener(onClickListener);
            }

        // Dersom man er "eier" av aktiviteten kan man slette den, hvis ikke vil slett-kanppen bli gjemt
        public void hideDelete(KalenderSideModel kalenderSideModel, int position) {
            if (meID == kalenderSideModel.getUserID()) {
                setDeleteOnActivity(kalenderSideModel, position);
                setClickOnSamtale(kalenderSideModel);
            } else {
                delete = itemView.findViewById(R.id.slettAktivitet);
                delete.setVisibility(View.INVISIBLE);

                kortID = itemView.findViewById(R.id.kortID);
                kortID.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Toast.makeText(contexten, "Dette er da ikke din aktivitet...?", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
            }
        }

        private void setClickOnSamtale(final KalenderSideModel kalenderSideModel) {
            kortID = itemView.findViewById(R.id.kortID);
            kortID.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(contexten);
                    builder.setTitle("Endre aktivitet")
                            .setMessage("Vil du endre aktiviteten: " + kalenderSideModel.getTheActivity() + "?");
                    builder.setPositiveButton("Mhm",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("dateFrom", kalenderSideModel.getDateFrom());
                                    bundle.putString("dateTo", kalenderSideModel.getDateTo());
                                    bundle.putString("timeFrom", kalenderSideModel.getTimeFrom());
                                    bundle.putString("timeTo", kalenderSideModel.getTimeTo());
                                    bundle.putString("theActivity", kalenderSideModel.getTheActivity());
                                    bundle.putInt("activityID", kalenderSideModel.getActivityID());
                                    Navigation.findNavController(kortID).navigate(R.id.action_kalenderFragment2_to_kalenderRedigerFragment, bundle);
                                }
                            });
                    builder.setNegativeButton("Nope",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.show();
                    return true;
                }
            });
        }
    }
}























