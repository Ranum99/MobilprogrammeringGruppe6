package com.example.mainactivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.model.HandlelisteModel;
import com.example.mainactivity.R;

import java.util.List;

public class HandlelisteAdapter extends RecyclerView.Adapter<HandlelisteAdapter.HandlelisteViewHolder> {

    private List<HandlelisteModel> HandlelisteListe;
    private LayoutInflater inflater;

    public HandlelisteAdapter(Context context, List<HandlelisteModel> HandlelisteListe) {
        this.inflater = LayoutInflater.from(context);
        this.HandlelisteListe = HandlelisteListe;
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
        viewHolder.setHandleliste(modelToDisplay);

    }

    @Override
    public int getItemCount() {
        return HandlelisteListe.size();
    }

    public class HandlelisteViewHolder extends RecyclerView.ViewHolder{
        private TextView nr;

        public HandlelisteViewHolder(@NonNull View itemView) {
            super(itemView);

            nr = itemView.findViewById(R.id.handlelistenummer);
        }

        public void setHandleliste(HandlelisteModel modelToDisplay) {
            nr.setText("Handleliste nr: " + modelToDisplay.getNr());
        }
    }
}
