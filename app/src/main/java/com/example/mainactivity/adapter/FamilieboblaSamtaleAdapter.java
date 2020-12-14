package com.example.mainactivity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.model.FamilieboblaSamtaleModel;
import com.example.mainactivity.R;
import com.example.mainactivity.User;
import java.util.List;

public class FamilieboblaSamtaleAdapter extends RecyclerView.Adapter<FamilieboblaSamtaleAdapter.FamilieboblaViewHolder>{

    private List<FamilieboblaSamtaleModel> MessageListe;
    private LayoutInflater inflater;
    private FamilieboblaSamtaleModel MessageToDisplay;
    private Context contexten;
    private SharedPreferences sharedPreferences;
    private int meID;

    public FamilieboblaSamtaleAdapter(Context context, List<FamilieboblaSamtaleModel> MessageListe) {
        this.inflater = LayoutInflater.from(context);
        this.MessageListe = MessageListe;
        this.contexten = context;
    }

    @NonNull
    @Override
    public FamilieboblaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        sharedPreferences = getContexten().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        View itemView = inflater.inflate(R.layout.familiebobla_samtale_message_item, parent, false);
        return new FamilieboblaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilieboblaSamtaleAdapter.FamilieboblaViewHolder viewHolder, int position) {
        MessageToDisplay = MessageListe.get(position);

        viewHolder.setMessage(MessageToDisplay);
    }

    @Override
    public int getItemCount() {
        return MessageListe.size();
    }

    public Context getContexten() {
        return contexten;
    }

    public class FamilieboblaViewHolder extends RecyclerView.ViewHolder {
        private TextView message;
        private CardView cardView;

        public FamilieboblaViewHolder(@NonNull final View itemView) {
            super(itemView);
        }

        public void setMessage(final FamilieboblaSamtaleModel SamtaleToDisplay) {
            message = itemView.findViewById(R.id.familieboblaSamtaleMessage);
            cardView = itemView.findViewById(R.id.familieboblaSamtale_cardView);

            String text = SamtaleToDisplay.getMessage();

            if (SamtaleToDisplay.getUserFromId() == meID) {
                // Dersom jeg har sendt meldingen blir boksen satt til høyre og fylt med bakgrunnsfarge
                cardView.setCardBackgroundColor(Color.parseColor("#09E5FF"));
                message.setTextColor(Color.parseColor("#FFFFFF"));

                // Setter boksen til høyre
                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) cardView.getLayoutParams();
                params.horizontalBias = 1f; // here is one modification for example. modify anything else you want :)
                cardView.setLayoutParams(params);
            } else {
                // Fyller en annen bakgrunnsfarge
                cardView.setCardBackgroundColor(Color.parseColor("#231f21"));
                message.setTextColor(Color.parseColor("#FFFFFF"));
            }
            message.setText(text);
        }
    }
}
