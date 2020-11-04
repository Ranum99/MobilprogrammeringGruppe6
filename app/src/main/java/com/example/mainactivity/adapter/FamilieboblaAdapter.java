package com.example.mainactivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.model.FamilieboblaModel;
import com.example.mainactivity.R;

import java.util.List;

public class FamilieboblaAdapter extends RecyclerView.Adapter<FamilieboblaAdapter.FamilieboblaViewHolder>{

    private List<FamilieboblaModel> SamtaleListe;
    private LayoutInflater inflater;

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
        FamilieboblaModel SamtaleToDisplay = SamtaleListe.get(position);

        viewHolder.setSamtale(SamtaleToDisplay);
    }

    @Override
    public int getItemCount() {
        return SamtaleListe.size();
    }

    public class FamilieboblaViewHolder extends RecyclerView.ViewHolder {

        private TextView navn, dato, mobil, aar;

        public FamilieboblaViewHolder(@NonNull View itemView) {
            super(itemView);

            navn = itemView.findViewById(R.id.FamilieBoblaNameCardview);

        }

        public void setSamtale(FamilieboblaModel SamtaleToDisplay) {
            navn.setText(SamtaleToDisplay.getNavn());

        }
    }
}
