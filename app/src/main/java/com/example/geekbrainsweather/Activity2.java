package com.example.geekbrainsweather;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {

    private TextView resultWindow;
    private String city;
    private boolean humidityState;
    private boolean airPressureState;
    private boolean windSpeedState;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initViews();
        readDataFromIntent();
        initResultWindow();
    }

    private void readDataFromIntent() {
        city = getIntent().getStringExtra("cityValue");
        humidityState = getIntent().getBooleanExtra("humidityState", false);
        airPressureState = getIntent().getBooleanExtra("airPressureState", false);
        windSpeedState = getIntent().getBooleanExtra("windSpeedState", false);
    }

    private void initResultWindow() {
        resultWindow.setText(printCityWeather(city));
    }

    private String printCityWeather(String city) {
        StringBuilder stringBuilder = new StringBuilder();
        switch (city) {
            case "Moscow":
                stringBuilder.append("Moscow\nTemperature : 25 degrees\n");
                if (humidityState) stringBuilder.append("Humidity : 75 %\n");
                if (airPressureState) stringBuilder.append("Air pressure : 1050 HPa\n");
                if (windSpeedState) stringBuilder.append("Wind speed : 5 m/sec\n");
                break;
            case "London":
                stringBuilder.append("London\nTemperature : 15 degrees\n");
                if (humidityState) stringBuilder.append("Humidity : 87 %\n");
                if (airPressureState) stringBuilder.append("Air pressure : 1000 HPa\n");
                if (windSpeedState) stringBuilder.append("Wind speed : 15 m/sec\n");
                break;
            case "Paris":
                stringBuilder.append("Paris\nTemperature : 5 degrees\n");
                if (humidityState) stringBuilder.append("Humidity : 60 %\n");
                if (airPressureState) stringBuilder.append("Air pressure : 950 HPa\n");
                if (windSpeedState) stringBuilder.append("Wind speed : 10 m/sec\n");
                break;
            default:
                stringBuilder.append("City not found!");
                break;
        }
        return stringBuilder.toString();
    }

    private void initViews() {
        resultWindow = findViewById(R.id.weatherInfoWindow);
    }
}
