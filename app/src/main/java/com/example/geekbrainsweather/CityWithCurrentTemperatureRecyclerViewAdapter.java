package com.example.geekbrainsweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CityWithCurrentTemperatureRecyclerViewAdapter
        extends RecyclerView.Adapter<CityWithCurrentTemperatureRecyclerViewAdapter.ViewHolder> {

    private ArrayList<CityWithCurrentTemperatureItem> addedCities = new ArrayList<>();

    public CityWithCurrentTemperatureRecyclerViewAdapter(ArrayList<CityWithCurrentTemperatureItem> addedCities) {
        if (addedCities != null) {
            this.addedCities = addedCities;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cardview_city_current, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.weatherThumbnailOnCityCard.setImageDrawable(addedCities.get(position).weatherThumbnail);
        holder.cityNameOnCityCard.setText(addedCities.get(position).cityName);
        holder.temperatureValueOnCityCard.setText(addedCities.get(position).currentTemperature);
    }

    @Override
    public int getItemCount() {
        return addedCities.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView weatherThumbnailOnCityCard;
        TextView cityNameOnCityCard;
        TextView temperatureValueOnCityCard;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            weatherThumbnailOnCityCard = itemView.findViewById(R.id.weatherThumbnailOnCityCard);
            cityNameOnCityCard = itemView.findViewById(R.id.cityNameOnCityCard);
            temperatureValueOnCityCard = itemView.findViewById(R.id.temperatureValueOnCityCard);
        }
    }
}
