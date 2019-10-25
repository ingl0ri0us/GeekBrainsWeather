package com.example.geekbrainsweather.fragments;


import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geekbrainsweather.DataClass;
import com.example.geekbrainsweather.R;
import com.example.geekbrainsweather.RecyclerViewAdapter;
import com.example.geekbrainsweather.SecondActivity;
import com.example.geekbrainsweather.WeatherDataLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SeveralPeriodsForecast extends Fragment {

    private String cityName;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        cityName = getArguments().getString("cityName");
        return inflater.inflate(R.layout.fragment_several_periods_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        new AsyncFetchFragment().execute();

    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_fragment);
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncFetchFragment extends AsyncTask<DataClass[], DataClass[], DataClass[]> {
        @Override
        protected DataClass[] doInBackground(DataClass[]... dataClasses) {
            DataClass[] result = null;
            try {
                final JSONObject jsonObject = WeatherDataLoader.getJSONData(cityName);

                if (jsonObject == null) {
                    result = new DataClass[]{
                            new DataClass(ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.alert_circle_dark),
                                    "Wrong City",
                                    "",
                                    "",
                                    "",
                                    "")};
                } else {
                    result = getDataArrayFromJson(jsonObject);
                }
            } catch (JSONException e) {
                Log.e("AsyncFetch", "JSONException");
                e.printStackTrace();
            }
            return result;
        }
        @Override
        protected void onPostExecute(DataClass[] dataClasses) {
            initRecyclerView(dataClasses);
        }
    }

    private DataClass[] getDataArrayFromJson(JSONObject jsonObject) throws JSONException {
        int forecastPeriods = jsonObject.getInt("cnt");
        JSONArray listOfForecasts = jsonObject.getJSONArray("list");

        DataClass[] dataFromJson = new DataClass[forecastPeriods];

        long sunrise = jsonObject.getJSONObject("city").getLong("sunrise") * 1000;
        long sunset = jsonObject.getJSONObject("city").getLong("sunset") * 1000;

        for (int i = 0; i < forecastPeriods; i++) {
            JSONObject currentForecastElement = listOfForecasts.getJSONObject(i);
            long timeOfForecastPeriod = currentForecastElement.getLong("dt") * 1000;

            Drawable icon = getWeatherIcon(currentForecastElement.getJSONArray("weather").getJSONObject(0), timeOfForecastPeriod, sunrise, sunset);
            String forecastPeriod = getForecastPeriod(currentForecastElement);
            String temperature = getTemperature(currentForecastElement.getJSONObject("main")) + " ℃";
            String humidityValue = getHumidity(currentForecastElement.getJSONObject("main")) + " %";
            String pressure = getPressure(currentForecastElement.getJSONObject("main")) + " hPa";
            String windSpeed = getWindSpeed(currentForecastElement.getJSONObject("wind")) + " m/sec";

            dataFromJson[i] = new DataClass(icon, forecastPeriod, temperature, humidityValue, pressure, windSpeed);
        }
        return dataFromJson;
    }

    private String getForecastPeriod(JSONObject jsonObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM HH:mm", Locale.getDefault());
        Date date = new Date(0);
        try {
            date = new Date(jsonObject.getLong("dt") * 1000);
        } catch (JSONException e) {
            Log.e("getForecastPeriod", "JSONException");
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }

    private String getTemperature(JSONObject jsonObject) {
        int temperatureValue = -999;
        try {
            temperatureValue = jsonObject.getInt("temp");
        } catch (JSONException e) {
            Log.e("getTemperature", "JSONException");
            e.printStackTrace();
        }
        return Integer.toString(temperatureValue);
    }

    private String getHumidity(JSONObject jsonObject) {
        double humidity = -1.0;
        try {
            humidity = jsonObject.getDouble("humidity");
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return Double.toString(humidity);
    }

    private String getWindSpeed(JSONObject jsonObject) {
        double windSpeed = -1.0;
        try {
            windSpeed = jsonObject.getDouble("speed");
        } catch (JSONException e) {
            Log.e("getWindSpeed", "JSONException");
            e.printStackTrace();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(windSpeed);
    }

    private String getPressure(JSONObject jsonObject) {
        double pressure = -1.0;
        try {
            pressure = jsonObject.getDouble("pressure");
        } catch (JSONException e) {
            Log.e("getPressure", "JSONException");
            e.printStackTrace();
        }
        return Double.toString(pressure);
    }

    private Drawable getWeatherIcon(JSONObject jsonObject, long timeOfForecastPeriod, long sunrise, long sunset) {
        int iconIdFromJson = -1;
        try {
            iconIdFromJson = jsonObject.getInt("id");
        } catch (JSONException e) {
            Log.e("getWeatherIcon", "JSONException");
            e.printStackTrace();
        }
        int id = iconIdFromJson / 100;

        Drawable iconToReturn;

        if (iconIdFromJson == 800) {
            if (timeOfForecastPeriod >= sunrise && timeOfForecastPeriod < sunset) {
                iconToReturn = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.weather_sunny_dark);
            } else {
                iconToReturn = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.weather_clear_night_dark);
            }
        } else {
            switch (id) {
                case 2: {
                    iconToReturn = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.weather_lightning_dark);
                    break;
                }
                case 3:
                case 5: {
                    iconToReturn = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.weather_pouring_dark);
                    break;
                }
                case 6: {
                    iconToReturn = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.snowflake_dark);
                    break;
                }
                case 7: {
                    iconToReturn = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.weather_fog_dark);
                    break;
                }
                case 8: {
                    iconToReturn = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.weather_partly_cloudy_dark);
                    break;
                }
                default: {
                    iconToReturn = ContextCompat.getDrawable(getActivity().getApplicationContext(), R.drawable.alert_circle_dark);
                    break;
                }
            }
        }
        return iconToReturn;
    }

    private void initRecyclerView(DataClass[] data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getBaseContext());
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}
