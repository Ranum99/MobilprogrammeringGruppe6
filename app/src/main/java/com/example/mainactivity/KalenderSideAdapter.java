package com.example.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class KalenderSideAdapter extends RecyclerView.Adapter<KalenderSideAdapter.KalenderViewHolder>{

    private List<KalenderSideModel> AktivitetsListe;
    private LayoutInflater inflater;
    private KalenderSideModel kalenderSideModel;
    private Context contexten;
    private Database database;

    public KalenderSideAdapter(Context context, List<KalenderSideModel> AktivitetsListe) {
        this.inflater = LayoutInflater.from(context);
        this.AktivitetsListe = AktivitetsListe;
        this.contexten = context;
    }

    @NonNull
    @Override
    public KalenderSideAdapter.KalenderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = inflater.inflate(R.layout.familiebobla_list_item, parent, false);
        return new KalenderSideAdapter.KalenderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull KalenderSideAdapter.KalenderViewHolder viewHolder, int position) {
        kalenderSideModel = AktivitetsListe.get(position);

        viewHolder.setAktivitet(kalenderSideModel);
        //viewHolder.setDeleteOnSamtale(AktivitetsListe, position);
        //viewHolder.setClickOnSamtale(AktivitetsListe);
    }

    @Override
    public int getItemCount() {
        return AktivitetsListe.size();
    }



    public class KalenderViewHolder extends RecyclerView.ViewHolder {

        private TextView navn, userName;
        private ConstraintLayout card;
        private ImageButton delete;

        public KalenderViewHolder(@NonNull final View itemView) {
            super(itemView);

        }

        public void setAktivitet(final KalenderSideModel ActivityToDisplay) {
            navn = itemView.findViewById(R.id.FamilieBoblaNameCardview);
            userName = itemView.findViewById(R.id.userNameFamiliebobla);

            navn.setText(ActivityToDisplay.getTheActivity());
            userName.setText("Aktivitet for: " + ActivityToDisplay.getUserName());
        }
    }
}























