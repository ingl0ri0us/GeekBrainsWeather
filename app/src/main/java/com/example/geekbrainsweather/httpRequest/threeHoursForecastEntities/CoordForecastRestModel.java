package com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities;

import com.google.gson.annotations.SerializedName;

class CoordForecastRestModel {
    @SerializedName("lat")
    public float lat;
    @SerializedName("lon")
    public float lon;
}
