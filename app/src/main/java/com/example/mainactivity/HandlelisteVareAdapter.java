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
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HandlelisteVareAdapter extends RecyclerView.Adapter<HandlelisteVareAdapter.HandlelisteVareViewHolder> {

    private List<HandlelisteVarerModel> varelisteListe;
    private LayoutInflater inflater;
    private Context context;
    private Database database;

    public HandlelisteVareAdapter(Context context, List<HandlelisteVarerModel> vareliste) {
        this.inflater = LayoutInflater.from(context);
        this.varelisteListe = vareliste;
        this.context = context;
    }

    @NonNull
    @Override
    public HandlelisteVareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.vare_list_item, parent, false);
        return new HandlelisteVareViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HandlelisteVareViewHolder holder, int position) {
        HandlelisteVarerModel modelToDisplay = varelisteListe.get(position);

        holder.setVare(modelToDisplay, position);

    }


    @Override
    public int getItemCount() {
        return 0;
    }


    public class HandlelisteVareViewHolder extends RecyclerView.ViewHolder {

        public HandlelisteVareViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private TextView vare;

        public void setVare(HandlelisteVarerModel modelToDisplay, int position) {

            vare = itemView.findViewById(R.id.vareText);
            vare.setText(modelToDisplay.getVare());


        }
    }
}