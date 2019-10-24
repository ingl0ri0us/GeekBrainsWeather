package com.example.geekbrainsweather;

import android.graphics.drawable.Drawable;

class DataClass {
    Drawable weatherThumbnail;
    String weekDay;
    String temperatureValue;
    String humidityValue, airPressureValue, windSpeedValue;

    DataClass(Drawable weatherThumbnail, String weekDay, String temperatureValue, String humidityValue, String airPressureValue, String windSpeedValue) {
        this.weatherThumbnail = weatherThumbnail;
        this.weekDay = weekDay;
        this.temperatureValue = temperatureValue;
        this.humidityValue = humidityValue;
        this.airPressureValue = airPressureValue;
        this.windSpeedValue = windSpeedValue;
    }
}
