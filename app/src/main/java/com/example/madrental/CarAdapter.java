package com.example.madrental;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.madrental.BDD.CarDTO;
import com.example.madrental.WS.RetourWS;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.CarViewHolder> {
    private List<RetourWS> listeCars;
    private MainActivity mainActivity = null;
    private FrameLayout frameLayoutConteneurDetail = null;

    public CarAdapter(MainActivity mainActivity, List<RetourWS> listeCars){
        this.listeCars = listeCars;
        this.mainActivity = mainActivity;
    }

    @Override
    public CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewCar = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item_liste, parent, false);
        return new CarViewHolder(viewCar);
    }

    @Override
    public void onBindViewHolder(CarViewHolder holder, int position) {
        holder.textViewCarName.setText(listeCars.get(position).nom);
        holder.textViewCarCategory.setText("Catégorie CO2: "+listeCars.get(position).categorieco2);
        holder.textViewCarPriceLocation.setText(listeCars.get(position).prixjournalierbase+ " € / jour");
        if (listeCars.get(position).image != null){
            Picasso.with(holder.imageViewCar.getContext()).load("http://s519716619.onlinehome.fr/exchange/madrental/images/"+listeCars.get(position).image).fit().centerCrop().into(holder.imageViewCar);
        }
    }

    public class CarViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewCarName;
        public TextView textViewCarPriceLocation;
        public TextView textViewCarCategory;
        public ImageView imageViewCar;

        public CarViewHolder(View itemView) {
            super(itemView);
            textViewCarName = itemView.findViewById(R.id.name_car);
            textViewCarPriceLocation = itemView.findViewById(R.id.price_car);
            textViewCarCategory = itemView.findViewById(R.id.category_car);
            imageViewCar = itemView.findViewById(R.id.image_car);
            ConstraintLayout constraintLayout = itemView.findViewById(R.id.fragment);

            constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainActivity.onClicItem(getAdapterPosition());
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return listeCars.size();
    }

    public RetourWS getItemParPosition(int position){
        return listeCars.get(position);
    }

}
