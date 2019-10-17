package com.example.geekbrainsweather;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {

    private String city;
    RecyclerView recyclerView;
    private DataClass[] forecastData;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        readDataFromIntent();
        initViews();
        setTitle(city);
        updateWeatherData(city);
        initRecyclerView();

    }

    private void readDataFromIntent() {
        city = getIntent().getStringExtra("cityValue");
    }

    private void initViews() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private void updateWeatherData(final String city) {
        new Thread() {
            @Override
            public void run() {
                final JSONObject jsonObject = WeatherDataLoader.getJSONData(city);

                if (jsonObject == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            forecastData = new DataClass[]{
                                    new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.alert_circle_dark),
                                            "No Such City",
                                            "",
                                            "",
                                            "",
                                            "")};
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                forecastData = getDataArrayFromJson(jsonObject);
                            } catch (JSONException e) {
                                Log.e("updateWeatherData", "JSONException");
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }.start();
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
            String temperature = getTemperature(currentForecastElement.getJSONObject("main"));
            String humidityValue = getHumidity(currentForecastElement.getJSONObject("main"));
            String pressure = getPressure(currentForecastElement.getJSONObject("main"));
            String windSpeed = getWindSpeed(currentForecastElement.getJSONObject("wind"));

            dataFromJson[i] = new DataClass(icon, forecastPeriod, temperature, humidityValue, pressure, windSpeed);
        }
        forecastData = dataFromJson;
        return dataFromJson;
    }

    private String getForecastPeriod(JSONObject jsonObject) {
        DateFormat dateFormat = DateFormat.getDateInstance();
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
        double temperatureValue = -999;
        try {
            temperatureValue = jsonObject.getDouble("temp");
        } catch (JSONException e) {
            Log.e("getTemperature", "JSONException");
            e.printStackTrace();
        }
        return Double.toString(temperatureValue);
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
        return Double.toString(windSpeed);
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
                iconToReturn = ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_sunny_dark);
            } else {
                iconToReturn = ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_clear_night_dark);
            }
        } else {
            switch (id) {
                case 2: {
                    iconToReturn = ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_lightning_dark);
                    break;
                }
                case 3:
                case 5: {
                    iconToReturn = ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_pouring_dark);
                    break;
                }
                case 6: {
                    iconToReturn = ContextCompat.getDrawable(getApplicationContext(), R.drawable.snowflake_dark);
                    break;
                }
                case 7: {
                    iconToReturn = ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_fog_dark);
                    break;
                }
                case 8: {
                    iconToReturn = ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_partly_cloudy_dark);
                    break;
                }
                default: {
                    iconToReturn = ContextCompat.getDrawable(getApplicationContext(), R.drawable.alert_circle_dark);
                    break;
                }
            }
        }
        return iconToReturn;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(forecastData);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}

