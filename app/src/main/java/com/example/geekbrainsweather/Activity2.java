package com.example.geekbrainsweather;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Activity2 extends AppCompatActivity {

    private TextView cityInfo;
    private String city;
    private boolean humidityState;
    private boolean airPressureState;
    private boolean windSpeedState;
    private TextView cityName;
    private TextView humidityLabel, humidityValueField;
    private TextView airPressureLabel, airPressureValueField;
    private TextView windSpeedLabel, windSpeedValueField;
    private ImageView imageSun,imageCloud,imageRain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        readDataFromIntent();
        initViews();
        showCityWeather(city);
    }

    private void readDataFromIntent() {
        city = getIntent().getStringExtra("cityValue");
        humidityState = getIntent().getBooleanExtra("humidityState", false);
        airPressureState = getIntent().getBooleanExtra("airPressureState", false);
        windSpeedState = getIntent().getBooleanExtra("windSpeedState", false);
    }

    private void showCityWeather(String city) {
        switch (city) {
            case "Moscow":
                cityName.setVisibility(View.VISIBLE);
                cityName.setText("Moscow");
                imageCloud.setVisibility(View.VISIBLE);
                cityInfo.setVisibility(View.VISIBLE);
                if (humidityState) {
                    humidityLabel.setVisibility(View.VISIBLE);
                    humidityValueField.setVisibility(View.VISIBLE);
                    humidityValueField.setText("75 %");
                }
                if (airPressureState) {
                    airPressureLabel.setVisibility(View.VISIBLE);
                    airPressureValueField.setVisibility(View.VISIBLE);
                    airPressureValueField.setText("1050 HPa");
                }
                if (windSpeedState) {
                    windSpeedLabel.setVisibility(View.VISIBLE);
                    windSpeedValueField.setVisibility(View.VISIBLE);
                    windSpeedValueField.setText("5 m/sec");
                }
                break;
            case "London":
                cityName.setVisibility(View.VISIBLE);
                cityName.setText("London");
                imageRain.setVisibility(View.VISIBLE);
                if (humidityState) {
                    humidityLabel.setVisibility(View.VISIBLE);
                    humidityValueField.setVisibility(View.VISIBLE);
                    humidityValueField.setText("87 %");
                }
                if (airPressureState) {
                    airPressureLabel.setVisibility(View.VISIBLE);
                    airPressureValueField.setVisibility(View.VISIBLE);
                    airPressureValueField.setText("1000 HPa");
                }
                if (windSpeedState) {
                    windSpeedLabel.setVisibility(View.VISIBLE);
                    windSpeedValueField.setVisibility(View.VISIBLE);
                    windSpeedValueField.setText("15 m/sec");
                }
                break;
            case "Paris":
                cityName.setVisibility(View.VISIBLE);
                cityName.setText("Paris");
                imageSun.setVisibility(View.VISIBLE);
                if (humidityState) {
                    humidityLabel.setVisibility(View.VISIBLE);
                    humidityValueField.setVisibility(View.VISIBLE);
                    humidityValueField.setText("60 %");
                }
                if (airPressureState) {
                    airPressureLabel.setVisibility(View.VISIBLE);
                    airPressureValueField.setVisibility(View.VISIBLE);
                    airPressureValueField.setText("950 HPa");
                }
                if (windSpeedState) {
                    windSpeedLabel.setVisibility(View.VISIBLE);
                    windSpeedValueField.setVisibility(View.VISIBLE);
                    windSpeedValueField.setText("10 m/sec");
                }
                break;
            default:
                cityName.setVisibility(View.VISIBLE);
                cityName.setText("City not found!");
                break;
        }
    }

    private void initViews() {
        cityInfo = findViewById(R.id.cityInfoWindow);
        cityName = findViewById(R.id.cityName);
        humidityLabel = findViewById(R.id.humidityLabel);
        humidityValueField = findViewById(R.id.humidityValueField);
        airPressureLabel = findViewById(R.id.airPressureLabel);
        airPressureValueField = findViewById(R.id.airPressureValueField);
        windSpeedLabel = findViewById(R.id.windSpeedLabel);
        windSpeedValueField = findViewById(R.id.windSpeedValueField);
        imageCloud = findViewById(R.id.imageCloud);
        imageRain = findViewById(R.id.imageRain);
        imageSun = findViewById(R.id.imageSun);
    }
}
