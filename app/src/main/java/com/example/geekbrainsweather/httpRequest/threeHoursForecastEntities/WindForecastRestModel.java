package com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities;

import com.google.gson.annotations.SerializedName;

public class WindForecastRestModel {
    @SerializedName("speed")
    public float speed;
    @SerializedName("deg")
    public int windDirection;
}
