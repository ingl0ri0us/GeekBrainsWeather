package com.example.geekbrainsweather.httpRequest.currentWeatherEntities;

import com.google.gson.annotations.SerializedName;

public class WeatherCurrentRequest {
    @SerializedName("coord")
    public CoordCurrentRestModel coordinates;
    @SerializedName("weather")
    public WeatherCurrentRestModel[] weather;
    @SerializedName("base")
    public String base;
    @SerializedName("main")
    public MainCurrentRestModel main;
    @SerializedName("visibility")
    public int visibility;
    @SerializedName("wind")
    public WindCurrentRestModel wind;
    @SerializedName("clouds")
    public CloudsCurrentRestModel clouds;
    @SerializedName("dt")
    public long dt;
    @SerializedName("sys")
    public SysCurrentRestModel sys;
    @SerializedName("id")
    public long id;
    @SerializedName("timezone")
    public int timezone;
    @SerializedName("name")
    public String name;
    @SerializedName("cod")
    public int cod;
}
