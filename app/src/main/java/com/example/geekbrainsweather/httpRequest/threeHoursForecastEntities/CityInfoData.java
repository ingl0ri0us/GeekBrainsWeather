package com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities;

import com.google.gson.annotations.SerializedName;

public class CityInfoData {
    @SerializedName("id")
    public long cityId;
    @SerializedName("name")
    public String cityName;
    @SerializedName("coord")
    public CoordForecastRestModel coordForecastRestModel;
    @SerializedName("country")
    public String countryCode;
    @SerializedName("population")
    public long population;
    @SerializedName("timezone")
    public int timezone;
    @SerializedName("sunrise")
    public long sunrise;
    @SerializedName("sunset")
    public long sunset;

}
