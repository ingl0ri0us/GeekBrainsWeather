package com.example.geekbrainsweather.httpRequest.currentWeatherEntities;

import com.google.gson.annotations.SerializedName;

public class MainCurrentRestModel {
    @SerializedName("temp")
    public float temp;
    @SerializedName("pressure")
    public int pressure;
    @SerializedName("temp_min")
    public float tempMin;
    @SerializedName("temp_max")
    public float tempMax;
}
