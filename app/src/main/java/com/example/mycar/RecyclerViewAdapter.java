package com.example.mycar;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.CarViewHolder> {

    public ArrayList<Car> getCarlist() {
        return carlist;
    }

    public void setCarlist(ArrayList<Car> carlist) {
        this.carlist = carlist;
    }

    ArrayList<Car> carlist;
    OnRecyclerViewItemClickListener listener;

    public RecyclerViewAdapter(ArrayList<Car> carlist, OnRecyclerViewItemClickListener listener) {
        this.carlist = carlist;
        this.listener=listener;

    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_layout, null, false);

        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = carlist.get(position);
        if (car.getImage() != null && car.getImage().isEmpty())
        holder.imageView.setImageURI(Uri.parse(car.getImage()));
        holder.tv_model.setText(car.getModel());
        holder.tv_color.setText(car.getColor());
        holder.tv_dpl.setText(String.valueOf(car.getDpl()));

        holder.id=car.getId();




    }

    @Override
    public int getItemCount() {
        return carlist.size();
    }

    class CarViewHolder extends RecyclerView.ViewHolder {

        TextView tv_model, tv_color, tv_dpl;
        ImageView imageView;

        int id;

        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.custom_car_imageView);
            tv_model = itemView.findViewById(R.id.custom_car_tv_model);
            tv_color = itemView.findViewById(R.id.custom_car_tv_color);
            tv_dpl = itemView.findViewById(R.id.custom_car_tv_dpl);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                     listener.onItemClicked(id);



                }
            });

        }
    }
}
