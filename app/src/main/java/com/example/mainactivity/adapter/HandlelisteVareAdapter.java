package com.example.mainactivity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.Database;
import com.example.mainactivity.model.HandlelisteVarerModel;
import com.example.mainactivity.R;
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

        database = new Database(context);

        return new HandlelisteVareViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HandlelisteVareViewHolder holder, int position) {
        HandlelisteVarerModel modelToDisplay = varelisteListe.get(position);

        holder.setVare(modelToDisplay);
        holder.setDelete(modelToDisplay, position);
        holder.setBought(modelToDisplay, position);
    }

    @Override
    public int getItemCount() {
        return varelisteListe.size();
    }

    public class HandlelisteVareViewHolder extends RecyclerView.ViewHolder {
        public HandlelisteVareViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        private TextView vare = itemView.findViewById(R.id.vareText);
        private CardView VareBoks = itemView.findViewById(R.id.VareBoks);
        private CheckBox checkBox = itemView.findViewById(R.id.handlelisteCheckBox);

        public void setVare(HandlelisteVarerModel modelToDisplay) {
            vare.setText(modelToDisplay.getVare());

            if (modelToDisplay.isChecked())
                checkBox.setChecked(true);
        }

        public void setDelete(final HandlelisteVarerModel modelToDisplay, final int position) {
            VareBoks.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Slett vare")
                            .setMessage("Er du sikker på at du vil slette denne varen?");
                    builder.setPositiveButton("Ja",
                            new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Sletter varen
                                    database = new Database(context);
                                    database.deleteRowFromTableById(Database.TABLE_HANDLELISTE_LISTE, modelToDisplay.getId());
                                    Log.i("HandlelisteVareAdapter", "Varen " + modelToDisplay.getVare() + " er slettet");
                                    removeItem(position);
                                }
                            });
                    builder.setNegativeButton("Nei",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    // Går ut av popup'en og tilbake til siden uten å gjøre noe
                                    Log.i("HandlelisteVareAdapter", "Varen " + modelToDisplay.getVare() + " ble ikke slettet");
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert1 = builder.create();
                    alert1.show();

                    return false;
                }
            });

        }
        public void setBought(final HandlelisteVarerModel modelToDisplay, int position) {
            VareBoks.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked())
                        checkBox.setChecked(false);
                    else
                        checkBox.setChecked(true);
                    setAsBought(modelToDisplay);
                }
            });
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setAsBought(modelToDisplay);
                }
            });
        }

        public void setAsBought(final HandlelisteVarerModel modelToDisplay) {
            int isChecked;
            if (checkBox.isChecked()) {
                isChecked = 1;
            } else {
                isChecked = 0;
            }
            System.out.println(isChecked);
            database.updateVareIsCheckedHandleliste(Integer.parseInt(modelToDisplay.getId()), isChecked);
        }

        private void removeItem(int position) {
            varelisteListe.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, varelisteListe.size());
        }
    }
}