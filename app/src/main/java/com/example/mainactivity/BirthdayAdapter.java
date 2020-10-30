package com.example.mainactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Calendar;
import java.util.List;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.BirthdayViewHolder>{

    private List<BirthdayModel> BirthdayList;
    private LayoutInflater inflater;
    private Calendar today = Calendar.getInstance();

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

        viewHolder.setBirthday(birthdayToDisplay);
    }

    @Override
    public int getItemCount() {
        return BirthdayList.size();
    }

    public class BirthdayViewHolder extends RecyclerView.ViewHolder {

        private TextView navn, dato, mobil, aar;

        public BirthdayViewHolder(@NonNull View itemView) {
            super(itemView);

            navn = itemView.findViewById(R.id.CardviewFullName);
            dato = itemView.findViewById(R.id.CardviewDate);
            mobil = itemView.findViewById(R.id.CardviewPhone);
            aar = itemView.findViewById(R.id.CardviewAge);

        }

        public void setBirthday(BirthdayModel birthdayToDisplay) {
            navn.setText(birthdayToDisplay.getNavn());
            dato.setText(birthdayToDisplay.getDato());
            mobil.setText(birthdayToDisplay.getMobil());

        }
    }

}
