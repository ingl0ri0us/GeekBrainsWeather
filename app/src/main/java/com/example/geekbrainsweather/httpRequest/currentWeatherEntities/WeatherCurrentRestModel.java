package com.example.geekbrainsweather.httpRequest.currentWeatherEntities;

import com.google.gson.annotations.SerializedName;

public class WeatherCurrentRestModel {
    @SerializedName("id")
    public int id;
    @SerializedName("main")
    public String main;
    @SerializedName("description")
    public String description;
    @SerializedName("icon")
    public String icon;
}
