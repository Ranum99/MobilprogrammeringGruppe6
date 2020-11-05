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

            viewHolder.setMatplan(matplanToDisplay, position);
        }

        @Override
        public int getItemCount() {
            return Matplanlist.size();
        }


        public class MatplanViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            private TextView uke;
            private int position;
            private MatplanModel matplan;
            private ImageButton delete;

            public MatplanViewHolder(@NonNull View itemView) {
                super(itemView);

                uke = itemView.findViewById(R.id.CardviewMatplan);

                itemView.setOnClickListener(this);

            }

            public void setMatplan(MatplanModel matplanToDisplay, int position) {
                uke.setText("Matplan uke " + matplanToDisplay.getUkenr());
                this.matplan = matplanToDisplay;
                this.position = position;

            }

            @Override
            public void onClick(View v) {
                final NavController navController = Navigation.findNavController(v);
                switch (v.getId()) {
                    default:
                        navController.navigate(R.id.matplanListeFragment);
                }
            }
        }
}
