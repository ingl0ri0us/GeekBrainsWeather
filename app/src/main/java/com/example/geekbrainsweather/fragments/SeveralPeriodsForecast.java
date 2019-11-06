package com.example.geekbrainsweather.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geekbrainsweather.ParseJsonToThreeHoursForecastArray;
import com.example.geekbrainsweather.ThreeHoursForecastItem;
import com.example.geekbrainsweather.R;
import com.example.geekbrainsweather.ThreeHoursForecastRecyclerViewAdapter;

import java.util.Objects;

public class SeveralPeriodsForecast extends Fragment {

    private String cityName;
    private RecyclerView recyclerView;
    // TODO: 2019-11-06 hide floating button
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        cityName = Objects.requireNonNull(getArguments()).getString("cityName");
        return inflater.inflate(R.layout.fragment_several_periods_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        Objects.requireNonNull(getActivity()).setTitle(cityName + " 3-hour forecast");
        new LoadCityForecastAndInitRecyclerView().execute(cityName);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_fragment);
    }

    @SuppressLint("StaticFieldLeak")
    private class LoadCityForecastAndInitRecyclerView extends AsyncTask<String, Void, ThreeHoursForecastItem[]> {
        @Override
        protected ThreeHoursForecastItem[] doInBackground(String... strings) {
            return getForecastArrayFromJson(strings[0]);
        }
        @Override
        protected void onPostExecute(ThreeHoursForecastItem[] threeHoursForecastItems) {
            initRecyclerView(threeHoursForecastItems);
        }
    }

    private ThreeHoursForecastItem[] getForecastArrayFromJson(String cityName) {
        ParseJsonToThreeHoursForecastArray parser = new ParseJsonToThreeHoursForecastArray(getContext(), cityName);
        return parser.getForecastArray();
    }

    private void initRecyclerView(ThreeHoursForecastItem[] data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getBaseContext());
        ThreeHoursForecastRecyclerViewAdapter adapter = new ThreeHoursForecastRecyclerViewAdapter(data);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
