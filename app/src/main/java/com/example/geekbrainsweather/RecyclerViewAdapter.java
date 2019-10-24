package com.example.geekbrainsweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private DataClass[] data = new DataClass[0];

    RecyclerViewAdapter(DataClass[] data) {
        if (data != null) {
            this.data = data;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.weatherThumbnail.setImageDrawable(data[position].weatherThumbnail);
        holder.dayOfWeekOnCard.setText(data[position].weekDay);
        holder.temperatureValueOnCard.setText(data[position].temperatureValue);
        holder.humidityValueOnCard.setText(data[position].humidityValue);
        holder.airPressureValueOnCard.setText(data[position].airPressureValue);
        holder.windSpeedValueOnCard.setText(data[position].windSpeedValue);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView weatherThumbnail;
        TextView dayOfWeekOnCard,temperatureValueOnCard;
        TextView humidityLabelOnCard, humidityValueOnCard;
        TextView airPressureLabelOnCard, airPressureValueOnCard;
        TextView windSpeedLabelOnCard, windSpeedValueOnCard;

        ViewHolder(View view) {
            super(view);

            weatherThumbnail = view.findViewById(R.id.weatherThumbnail);
            dayOfWeekOnCard = view.findViewById(R.id.dayOfWeekOnCard);
            temperatureValueOnCard = view.findViewById(R.id.temperatureValueOnCard);

            humidityLabelOnCard = view.findViewById(R.id.humidityLabelOnCard);
            humidityLabelOnCard.setText(R.string.humidity);

            humidityValueOnCard = view.findViewById(R.id.humidityValueOnCard);

            airPressureLabelOnCard = view.findViewById(R.id.airPressureLabelOnCard);
            airPressureLabelOnCard.setText(R.string.air_pressure);

            airPressureValueOnCard = view.findViewById(R.id.airPressureValueOnCard);

            windSpeedLabelOnCard = view.findViewById(R.id.windSpeedLabelOnCard);
            windSpeedLabelOnCard.setText(R.string.wind_speed);

            windSpeedValueOnCard = view.findViewById(R.id.windSpeedValueOnCard);
        }
    }
}

