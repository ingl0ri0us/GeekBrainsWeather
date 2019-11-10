package com.example.geekbrainsweather.httpRequest.currentWeatherEntities;

import com.google.gson.annotations.SerializedName;

public class SysCurrentRestModel {
    @SerializedName("type")
    public int type;
    @SerializedName("id")
    public int id;
    @SerializedName("country")
    public String country;
    @SerializedName("sunrise")
    public long sunrise;
    @SerializedName("sunset")
    public long sunset;
}
