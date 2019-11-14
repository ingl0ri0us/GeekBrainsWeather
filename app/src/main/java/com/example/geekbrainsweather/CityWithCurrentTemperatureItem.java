package com.example.geekbrainsweather;

import android.graphics.drawable.Drawable;

public class CityWithCurrentTemperatureItem {

    Drawable weatherThumbnail;
    int thumbnailId;
    String cityName;
    String currentTemperature;
    int temperatureValue;
    long timeUpdated;

    CityWithCurrentTemperatureItem(Drawable weatherThumbnail, String cityName, String currentTemperature) {
        this.weatherThumbnail = weatherThumbnail;
        this.cityName = cityName;
        this.currentTemperature = currentTemperature;
    }

    public CityWithCurrentTemperatureItem(int thumbnailId, String cityName, int temperatureValue, long timeUpdated) {
        this.thumbnailId = thumbnailId;
        this.cityName = cityName;
        this.temperatureValue = temperatureValue;
        this.timeUpdated = timeUpdated;
    }

    public String getCityName() {
        return cityName;
    }

    public int getThumbnailId() {
        return thumbnailId;
    }

    public long getTimeUpdated() {
        return timeUpdated;
    }

    public int getTemperatureValue() {
        return temperatureValue;
    }
}
