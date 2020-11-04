package com.example.mainactivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.model.OnskelisteModel;
import com.example.mainactivity.R;

import java.util.List;

public class OnskelisteAdapter extends RecyclerView.Adapter<OnskelisteAdapter.OnskelisteViewHolder> {

    private List<OnskelisteModel> Onskelister;
    private LayoutInflater inflater;

    public OnskelisteAdapter(Context context, List<OnskelisteModel> Onskelister) {
        this.inflater = LayoutInflater.from(context);
        this.Onskelister = Onskelister;
    }

    @NonNull
    @Override
    public OnskelisteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = inflater.inflate(R.layout.onskeliste_list_item, parent, false);

        return new OnskelisteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OnskelisteViewHolder viewHolder, int position) {
        OnskelisteModel modelToDisplay = Onskelister.get(position);

        viewHolder.setOnskeliste(modelToDisplay);
    }

    @Override
    public int getItemCount() {
        return Onskelister.size();
    }

    public class OnskelisteViewHolder extends RecyclerView.ViewHolder {

        private TextView navn;

        public OnskelisteViewHolder(@NonNull View itemView) {
            super(itemView);

            navn = itemView.findViewById(R.id.Onskelistenavn);

        }

        public void setOnskeliste(OnskelisteModel modelToDisplay) {
            navn.setText(modelToDisplay.getNavn());

        }
    }

}
