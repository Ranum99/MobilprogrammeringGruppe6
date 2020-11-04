package com.example.mainactivity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainactivity.model.MatplanModel;
import com.example.mainactivity.R;

import java.util.List;

public class MatplanAdapter extends RecyclerView.Adapter<MatplanAdapter.MatplanViewHolder> {

        private List<MatplanModel> Matplanlist;
        private LayoutInflater inflater;

        public MatplanAdapter(Context context, List<MatplanModel> MatplanList) {
            this.inflater = LayoutInflater.from(context);
            this.Matplanlist = MatplanList;

        }

        @NonNull
        @Override
        public MatplanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
            View itemView = inflater.inflate(R.layout.matplan_list_item, parent, false);

            return new MatplanViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MatplanViewHolder viewHolder, int position) {
            MatplanModel matplanToDisplay = Matplanlist.get(position);

            viewHolder.setMatplan(matplanToDisplay);
        }

        @Override
        public int getItemCount() {
            return Matplanlist.size();
        }

        public class MatplanViewHolder extends RecyclerView.ViewHolder {

            private TextView uke;

            public MatplanViewHolder(@NonNull View itemView) {
                super(itemView);

                uke = itemView.findViewById(R.id.CardviewMatplan);


            }

            public void setMatplan(MatplanModel matplanToDisplay) {
                uke.setText("Matplan uke " + matplanToDisplay.getUkenr());

            }
        }
}
