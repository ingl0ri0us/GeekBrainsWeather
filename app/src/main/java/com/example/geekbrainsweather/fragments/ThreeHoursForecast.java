package com.example.geekbrainsweather.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geekbrainsweather.ThreeHoursForecastItem;
import com.example.geekbrainsweather.R;
import com.example.geekbrainsweather.ThreeHoursForecastParser;
import com.example.geekbrainsweather.ThreeHoursForecastRecyclerViewAdapter;
import com.example.geekbrainsweather.httpRequest.OpenWeatherRepo;
import com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities.ThreeHoursForecastRequest;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThreeHoursForecast extends Fragment {

    private String cityName;
    private RecyclerView recyclerView;
    private ThreeHoursForecastItem[] forecastDataArray;

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

        fillForecastArrayAndInitRecyclerView(cityName);
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_fragment);
    }

    private void initRecyclerView(ThreeHoursForecastItem[] data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getBaseContext());
        ThreeHoursForecastRecyclerViewAdapter adapter = new ThreeHoursForecastRecyclerViewAdapter(data);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void fillForecastArrayAndInitRecyclerView(String cityName) {
        String API_KEY = "35c744e8949e353ebb9961393941c850";
        String MEASURE_UNITS = "metric";
        OpenWeatherRepo
                .getSingleton()
                .getAPI()
                .loadForecastWeather(cityName, API_KEY, MEASURE_UNITS)
                .enqueue(new Callback<ThreeHoursForecastRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<ThreeHoursForecastRequest> call, @NonNull Response<ThreeHoursForecastRequest> response) {
                        if(response.body() !=  null && response.isSuccessful()) {
                            ThreeHoursForecastRequest mainModel = response.body();
                            Context context = getContext();
                            ThreeHoursForecastParser parser = new ThreeHoursForecastParser(mainModel,context);
                            forecastDataArray = parser.getForecastDataArray();
                            initRecyclerView(forecastDataArray);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ThreeHoursForecastRequest> call, @NonNull Throwable t) {
                        forecastDataArray = new ThreeHoursForecastItem[] {new ThreeHoursForecastItem(
                                ContextCompat.getDrawable(Objects.requireNonNull(getContext()), R.drawable.alert_circle_dark),
                                "Wrong City",
                                "",
                                "",
                                "",
                                "")};
                        initRecyclerView(forecastDataArray);
                    }
                });
    }
}
