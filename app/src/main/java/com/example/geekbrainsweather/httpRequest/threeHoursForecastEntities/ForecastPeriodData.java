package com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities;

import com.google.gson.annotations.SerializedName;

public class ForecastPeriodData {
    @SerializedName("dt")
    public long dt;
    @SerializedName("main")
    public MainForecastRestModel mainForecastRestModel;
    @SerializedName("weather")
    public WeatherForecastRestModel[] weatherForecastRestModels;
    @SerializedName("clouds")
    public CloudsForecastRestModel cloudsForecastRestModel;
    @SerializedName("wind")
    public WindForecastRestModel windForecastRestModel;
    @SerializedName("sys")
    public SysForecastRestModel sysForecastRestModel;
    @SerializedName("dt_txt")
    public String dateTimeInTxtFormat;
}
