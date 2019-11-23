package com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities;

import com.google.gson.annotations.SerializedName;

public class ThreeHoursForecastRequest {
    @SerializedName("cod")
    public int cod;
    @SerializedName("message")
    public String message;
    @SerializedName("cnt")
    public int amountOfForecastPeriods;
    @SerializedName("list")
    public ForecastPeriodData[] listOfForecastPeriods;
    @SerializedName("city")
    public CityInfoData cityInfoData;
}
