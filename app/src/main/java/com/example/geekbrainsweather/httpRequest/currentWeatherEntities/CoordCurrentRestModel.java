package com.example.geekbrainsweather.httpRequest.currentWeatherEntities;

import com.google.gson.annotations.SerializedName;

public class CoordCurrentRestModel {
    @SerializedName("lon")
    public float lon;
    @SerializedName("lat")
    public float lat;
}
