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
    private boolean temperatureState;
    private boolean cityInfoState;
    private TextView cityName;
    private TextView temperatureValueField;
    private TextView humidityLabel, humidityValueField;
    private TextView airPressureLabel, airPressureValueField;
    private TextView windSpeedLabel, windSpeedValueField;
    private ImageView imageSun, imageCloud, imageRain;

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
        temperatureState = getIntent().getBooleanExtra("temperatureState", false);
        humidityState = getIntent().getBooleanExtra("humidityState", false);
        airPressureState = getIntent().getBooleanExtra("airPressureState", false);
        windSpeedState = getIntent().getBooleanExtra("windSpeedState", false);
        cityInfoState = getIntent().getBooleanExtra("cityInfoState", false);
    }

    private void showCityWeather(String city) {
        if (city.equals("Moscow") || city.equals("Москва")) {
            cityName.setVisibility(View.VISIBLE);
            cityName.setText(R.string.cityNameMoscow);
            imageCloud.setVisibility(View.VISIBLE);
            if (temperatureState) {
                temperatureValueField.setVisibility(View.VISIBLE);
                temperatureValueField.setText(R.string.moscowTemperatureValue);
            }
            if (humidityState) {
                humidityLabel.setVisibility(View.VISIBLE);
                humidityValueField.setVisibility(View.VISIBLE);
                humidityValueField.setText(R.string.moscowHumidityValue);
            }
            if (airPressureState) {
                airPressureLabel.setVisibility(View.VISIBLE);
                airPressureValueField.setVisibility(View.VISIBLE);
                airPressureValueField.setText(R.string.moscowPressureValue);
            }
            if (windSpeedState) {
                windSpeedLabel.setVisibility(View.VISIBLE);
                windSpeedValueField.setVisibility(View.VISIBLE);
                windSpeedValueField.setText(R.string.moscowWindSpeedValue);
            }
            if (cityInfoState) {
                cityInfo.setVisibility(View.VISIBLE);
                cityInfo.setText(R.string.city_info_Moscow);
            }
        } else if (city.equals("London") || city.equals("Лондон")) {
            cityName.setVisibility(View.VISIBLE);
            cityName.setText(R.string.londonCityName);
            imageRain.setVisibility(View.VISIBLE);
            if (temperatureState) {
                temperatureValueField.setVisibility(View.VISIBLE);
                temperatureValueField.setText(R.string.londonTemperatureValue);
            }
            if (humidityState) {
                humidityLabel.setVisibility(View.VISIBLE);
                humidityValueField.setVisibility(View.VISIBLE);
                humidityValueField.setText(R.string.londonHumidityValue);
            }
            if (airPressureState) {
                airPressureLabel.setVisibility(View.VISIBLE);
                airPressureValueField.setVisibility(View.VISIBLE);
                airPressureValueField.setText(R.string.londonPressureValue);
            }
            if (windSpeedState) {
                windSpeedLabel.setVisibility(View.VISIBLE);
                windSpeedValueField.setVisibility(View.VISIBLE);
                windSpeedValueField.setText(R.string.londonWindSpeed);
            }
            if (cityInfoState) {
                cityInfo.setVisibility(View.VISIBLE);
                cityInfo.setText(R.string.city_info_London);
            }
        } else if (city.equals("Paris") || city.equals("Париж")) {
            cityName.setVisibility(View.VISIBLE);
            cityName.setText(R.string.parisCityName);
            imageSun.setVisibility(View.VISIBLE);
            if (temperatureState) {
                temperatureValueField.setVisibility(View.VISIBLE);
                temperatureValueField.setText(R.string.city_info_Paris);
            }
            if (humidityState) {
                humidityLabel.setVisibility(View.VISIBLE);
                humidityValueField.setVisibility(View.VISIBLE);
                humidityValueField.setText(R.string.parisHumidityValue);
            }
            if (airPressureState) {
                airPressureLabel.setVisibility(View.VISIBLE);
                airPressureValueField.setVisibility(View.VISIBLE);
                airPressureValueField.setText(R.string.parisPressureValue);
            }
            if (windSpeedState) {
                windSpeedLabel.setVisibility(View.VISIBLE);
                windSpeedValueField.setVisibility(View.VISIBLE);
                windSpeedValueField.setText(R.string.parisWindSpeedValue);
            }
            if (cityInfoState) {
                cityInfo.setVisibility(View.VISIBLE);
                cityInfo.setText(R.string.city_info_Paris);
            }
        } else {
            cityName.setVisibility(View.VISIBLE);
            cityName.setText(R.string.cityNotFound);
        }
    }

    private void initViews() {
        cityInfo = findViewById(R.id.cityInfoWindow);
        cityName = findViewById(R.id.cityName);
        temperatureValueField = findViewById(R.id.temperatureValue);
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
