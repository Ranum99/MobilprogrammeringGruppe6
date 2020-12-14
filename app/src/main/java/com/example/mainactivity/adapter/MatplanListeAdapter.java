package com.example.mainactivity.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mainactivity.Database;
import com.example.mainactivity.model.MatplanListeModel;
import com.example.mainactivity.R;
import com.example.mainactivity.model.User;
import java.util.List;

public class MatplanListeAdapter extends RecyclerView.Adapter<MatplanListeAdapter.MatplanListeViewHolder>{
    private List<MatplanListeModel> MatplanListe;
    private LayoutInflater inflater;
    private MatplanListeModel MatplanToDisplay;
    private Context contexten;
    private SharedPreferences sharedPreferences;
    private Database database;

    public MatplanListeAdapter(Context context, List<MatplanListeModel> MessageListe) {
        this.inflater = LayoutInflater.from(context);
        this.MatplanListe = MessageListe;
        this.contexten = context;
    }

    @NonNull
    @Override
    public MatplanListeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        database = new Database(contexten);
        sharedPreferences = getContexten().getSharedPreferences(User.SESSION, Context.MODE_PRIVATE);
        int meID = Integer.parseInt(sharedPreferences.getString(User.ID, null));

        View itemView = inflater.inflate(R.layout.matplan_liste_list_item, parent, false);
        return new MatplanListeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MatplanListeAdapter.MatplanListeViewHolder viewHolder, int position) {
        MatplanToDisplay = MatplanListe.get(position);

        viewHolder.setMatplan(MatplanToDisplay);
        if (MatplanToDisplay.getFood() != null)
            viewHolder.setFood(MatplanToDisplay);
        viewHolder.setFocusChange(MatplanToDisplay);
    }

    @Override
    public int getItemCount() {
        return MatplanListe.size();
    }

    public Context getContexten() {
        return contexten;
    }

    public class MatplanListeViewHolder extends RecyclerView.ViewHolder {
        private TextView day;
        private EditText food;
        private CardView cardView;

        public MatplanListeViewHolder(@NonNull final View itemView) {
            super(itemView);
        }

        // Setter hvilken dag i matplanen det er
        public void setMatplan(final MatplanListeModel MatplanToDisplay) {
            day = itemView.findViewById(R.id.dayOfWeek);
            day.setText(MatplanToDisplay.getDay());
        }

        // Setter hva mat er for dagen, dersom det ikke er null
        public void setFood(MatplanListeModel matplanToDisplay) {
            food = itemView.findViewById(R.id.foodInMatplan);
            food.setText(matplanToDisplay.getFood());
        }

        // Dersom en bruker går bort i fra en editText sjekker den om man har endret noe.
            // Dersom man har endret noe, oppdateres databasen
            // Dersom man ikke har endret noe, skjer det ingen ting
        public void setFocusChange(final MatplanListeModel matplanToDisplay) {
            food = itemView.findViewById(R.id.foodInMatplan);

            final String before = food.getText().toString();

            food.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus && !before.equals(food.getText().toString())) {
                        updateFood();
                    }
                }

                private void updateFood() {
                    boolean updateFood = database.updateFoodInMatplan(matplanToDisplay.getSubMatplanID(), food.getText().toString());

                    if (updateFood) {
                        Log.i("MatplanListeAdapter", "Matrett på " + matplanToDisplay.getDay() + " ble oppdatert til " + food.getText().toString());
                    } else
                        Log.e("MatplanListeAdapter", "Matrett på " + matplanToDisplay.getDay() + " ble IKKE oppdatert");
                }
            });
        }
    }

}
