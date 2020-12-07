package com.example.mainactivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HandlelisteVareAdapter extends RecyclerView.Adapter<HandlelisteVareAdapter.HandlelisteVareViewHolder> {

    private List<HandlelisteVarerModel> varelisteListe;
    private LayoutInflater inflater;
    private Context context;
    private Database database;
    private SharedPreferences sharedPreferences;
    private HandlelisteVarerModel modelToDisplay;

    public HandlelisteVareAdapter(Context context, List<HandlelisteVarerModel> vareliste) {
        this.inflater = LayoutInflater.from(context);
        this.varelisteListe = vareliste;
        this.context = context;
    }

    @NonNull
    @Override
    public HandlelisteVareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.vare_list_item, parent, false);

        database = new Database(context);
        sharedPreferences = context.getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);

        return new HandlelisteVareViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HandlelisteVareViewHolder holder, int position) {
        modelToDisplay = varelisteListe.get(position);

        holder.setVare(modelToDisplay);
        holder.setDelete(modelToDisplay, position);

    }

    @Override
    public int getItemCount() {
        return varelisteListe.size();
    }


    public class HandlelisteVareViewHolder extends RecyclerView.ViewHolder {

        public HandlelisteVareViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private TextView vare;

        public void setVare(HandlelisteVarerModel modelToDisplay) {

            vare = itemView.findViewById(R.id.vareText);
            vare.setText(modelToDisplay.getVare());

        }

        public void setDelete(final HandlelisteVarerModel modelToDisplay, final int position) {
            new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {

                    database.deleteRowFromTableById(Database.TABLE_HANDLELISTE_LISTE, modelToDisplay.getId());
                    removeItem(position);
                    return true;
                }

            };

        }

        private void removeItem(int position) {
            varelisteListe.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, varelisteListe.size());
        }
    }
}