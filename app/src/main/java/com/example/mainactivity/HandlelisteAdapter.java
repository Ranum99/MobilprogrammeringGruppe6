package com.example.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

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
        viewHolder.setHandleliste(modelToDisplay, position);

    }

    @Override
    public int getItemCount() {
        return HandlelisteListe.size();
    }

    private void removeItem(int position) {
        HandlelisteListe.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, HandlelisteListe.size());
    }

    public class HandlelisteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nr;
        private ImageButton delete;
        private int position;
        private HandlelisteModel handleliste;


        public HandlelisteViewHolder(@NonNull View itemView) {
            super(itemView);

            nr = itemView.findViewById(R.id.handlelistenummer);
            delete = itemView.findViewById(R.id.handlelisteDelete);

            delete.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        public void setHandleliste(HandlelisteModel modelToDisplay, int position) {
            nr.setText("Handleliste nr: " + modelToDisplay.getNr());
            this.position = position;
            this.handleliste = modelToDisplay;
        }

        @Override
        public void onClick(View view) {
            final NavController navController = Navigation.findNavController(view);
            switch (view.getId()) {
                case R.id.handlelisteDelete:
                    removeItem(position);
                    break;
                default:
                    navController.navigate(R.id.handlelisteListeFragment);
            }

        }
    }
}
