package com.example.geekbrainsweather;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CityWithCurrentTemperatureRecyclerViewAdapter
        extends RecyclerView.Adapter<CityWithCurrentTemperatureRecyclerViewAdapter.ViewHolder> {

    private ArrayList<CityWithCurrentTemperatureItem> addedCities = new ArrayList<>();
    private OnCityClickListener onCityClickListener;
    private CityWithCurrentTemperatureItem recentlyDeletedItem;
    private int recentlyDeletedItemPosition;
    private Activity activity;

    public CityWithCurrentTemperatureRecyclerViewAdapter(Activity activity, ArrayList<CityWithCurrentTemperatureItem> addedCities, OnCityClickListener onCityClickListener) {
        if (addedCities != null) {
            this.addedCities = addedCities;
            this.onCityClickListener = onCityClickListener;
            this.activity = activity;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.cardview_city_current, parent, false);
        return new ViewHolder(view, onCityClickListener);
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

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView weatherThumbnailOnCityCard;
        TextView cityNameOnCityCard;
        TextView temperatureValueOnCityCard;
        OnCityClickListener onCityClickListener;

        ViewHolder(@NonNull View itemView, OnCityClickListener onCityClickListener) {
            super(itemView);

            weatherThumbnailOnCityCard = itemView.findViewById(R.id.weatherThumbnailOnCityCard);
            cityNameOnCityCard = itemView.findViewById(R.id.cityNameOnCityCard);
            temperatureValueOnCityCard = itemView.findViewById(R.id.temperatureValueOnCityCard);
            this.onCityClickListener = onCityClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCityClickListener.onCityClick(getAdapterPosition());
        }
    }

    public interface OnCityClickListener {
        void onCityClick(int position);
    }

    void deleteItem(int position) {
        recentlyDeletedItem = addedCities.get(position);
        recentlyDeletedItemPosition = position;
        addedCities.remove(position);
        notifyItemRemoved(position);
        showUndoSnackBar(recentlyDeletedItem.getCityName());
    }

    private void showUndoSnackBar(String deletedCity) {
        View view = activity.findViewById(R.id.mainActivityCoordinatorLayout);
        Snackbar snackbar = Snackbar.make(view, deletedCity + " was deleted.", Snackbar.LENGTH_LONG);
        snackbar.setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoDelete();
            }
        });
        snackbar.show();
    }

    private void undoDelete() {
        addedCities.add(recentlyDeletedItemPosition,recentlyDeletedItem);
        notifyItemInserted(recentlyDeletedItemPosition);
    }
}
