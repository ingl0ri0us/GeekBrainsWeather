package com.example.geekbrainsweather;

import android.graphics.drawable.Drawable;

public class CityWithCurrentTemperatureItem {

    Drawable weatherThumbnail;
    String cityName;
    String currentTemperature;

    CityWithCurrentTemperatureItem(Drawable weatherThumbnail, String cityName, String currentTemperature) {
        this.weatherThumbnail = weatherThumbnail;
        this.cityName = cityName;
        this.currentTemperature = currentTemperature;
    }
}
