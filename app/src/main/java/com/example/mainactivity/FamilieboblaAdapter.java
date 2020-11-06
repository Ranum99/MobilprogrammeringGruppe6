package com.example.mainactivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FamilieboblaAdapter extends RecyclerView.Adapter<FamilieboblaAdapter.FamilieboblaViewHolder>{

    private List<FamilieboblaModel> SamtaleListe;
    private LayoutInflater inflater;
    private ArrayList<ConstraintLayout> cards = new ArrayList<>();
    private FamilieboblaModel SamtaleToDisplay;

    public FamilieboblaAdapter(Context context, List<FamilieboblaModel> SamtaleListe) {
        this.inflater = LayoutInflater.from(context);
        this.SamtaleListe = SamtaleListe;
    }

    @NonNull
    @Override
    public FamilieboblaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = inflater.inflate(R.layout.familiebobla_list_item, parent, false);



        return new FamilieboblaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilieboblaAdapter.FamilieboblaViewHolder viewHolder, int position) {
        SamtaleToDisplay = SamtaleListe.get(position);



        viewHolder.setSamtale(SamtaleToDisplay);
    }

    @Override
    public int getItemCount() {
        return SamtaleListe.size();
    }

    public class FamilieboblaViewHolder extends RecyclerView.ViewHolder {

        private TextView navn, dato, mobil, aar;
        private ConstraintLayout card;
        private ImageButton delete;

        public FamilieboblaViewHolder(@NonNull View itemView) {
            super(itemView);

            navn = itemView.findViewById(R.id.FamilieBoblaNameCardview);

            card = itemView.findViewById(R.id.cardID);
            Bundle bundle = new Bundle();
            bundle.putString("name", getNavn());
            card.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_familieboblaFragment_to_familieboblaSamtaleFragment, bundle));


            delete = itemView.findViewById(R.id.imageButton);
            View.OnClickListener nene = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("meme");
                }
            };

            delete.setOnClickListener(nene);
        }

        public void setSamtale(FamilieboblaModel SamtaleToDisplay) {
            navn.setText(SamtaleToDisplay.getNavn());
        }

        public String getNavn() {
            return navn.getText().toString();
        }
    }

}
