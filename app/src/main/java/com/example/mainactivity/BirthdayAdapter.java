package com.example.mainactivity;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.BirthdayViewHolder>{

    private List<BirthdayModel> BirthdayList;
    private LayoutInflater inflater;

    public BirthdayAdapter(Context context, List<BirthdayModel> BirthdayList) {
        this.inflater = LayoutInflater.from(context);
        this.BirthdayList = BirthdayList;

    }

    @NonNull
    @Override
    public BirthdayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View itemView = inflater.inflate(R.layout.birthday_list_item, parent, false);
        return new BirthdayViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayViewHolder viewHolder, int position) {
        BirthdayModel birthdayToDisplay = BirthdayList.get(position);
        viewHolder.setBirthday(birthdayToDisplay, position);
    }

    @Override
    public int getItemCount() {
        return BirthdayList.size();
    }

    private void removeItem(int position) {
        BirthdayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, BirthdayList.size());
    }

    public class BirthdayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView navn, dato, mobil, aar;
        private int position;
        private BirthdayModel bursdag;
        private ImageButton delete;

        public BirthdayViewHolder(@NonNull View itemView) {
            super(itemView);

            navn = itemView.findViewById(R.id.CardviewFullName);
            dato = itemView.findViewById(R.id.CardviewDate);
            mobil = itemView.findViewById(R.id.CardviewPhone);
            aar = itemView.findViewById(R.id.CardviewAge);
            delete = itemView.findViewById(R.id.deleteBursdag);

            delete.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        public void setBirthday(BirthdayModel birthdayToDisplay, int position) {
            navn.setText(birthdayToDisplay.getNavn());
            dato.setText(birthdayToDisplay.getDato());
            mobil.setText(birthdayToDisplay.getMobil());

            this.bursdag = birthdayToDisplay;
            this.position = position;

        }

        @Override
        public void onClick(View view) {
            final NavController navController = Navigation.findNavController(view);
            switch (view.getId()) {
                case R.id.deleteBursdag:
                    removeItem(position);
                    break;
                default:
                    navController.navigate(R.id.bursdagLeggTilFragment);
            }

        }

    }

}
