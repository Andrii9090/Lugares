package com.example.mislugaresfavoritos;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintsChangedListener;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RVAdapterLugares extends RecyclerView.Adapter<RVAdapterLugares.ViewHolder> {
    ArrayList<Place> places;
    Context context;
    DataToActivityPlaceList dataPlaceList;

    RVAdapterLugares(ArrayList<Place> lugares) {
        this.places = lugares;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        dataPlaceList = (DataToActivityPlaceList)context;
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lugar, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.name.setText(places.get(position).getName());
        Date dateCreate = new Date(Long.parseLong(places.get(position).getDate_create()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        holder.date_create.setText(dateFormat.format(dateCreate));
        holder.ratingBar.setRating((float) places.get(position).getRating());
        holder.item.setId(position);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataPlaceList.getPosition(v.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.places.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView date_create;
        private RatingBar ratingBar;
        private ConstraintLayout item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = (ConstraintLayout) itemView.findViewById(R.id.item_place);
            name = (TextView) itemView.findViewById(R.id.item_lugar_name);
            date_create = (TextView) itemView.findViewById(R.id.item_lugar_date_create);
            ratingBar = (RatingBar) itemView.findViewById(R.id.item_lugar_rating);
        }
    }

    interface DataToActivityPlaceList{
        void getPosition(int position);
    }
}
