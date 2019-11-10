package com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities;

import com.google.gson.annotations.SerializedName;

public class MainForecastRestModel {
    @SerializedName("temp")
    public float temperature;
    @SerializedName("temp_min")
    public float minTemperature;
    @SerializedName("temp_max")
    public float maxTemperature;
    @SerializedName("pressure")
    public int pressure;
    @SerializedName("sea_level")
    public int seaLevelPressure;
    @SerializedName("grnd_level")
    public int groundLevelPressure;
    @SerializedName("humidity")
    public int humidity;
    @SerializedName("temp_kf")
    public float tempKf;
}
