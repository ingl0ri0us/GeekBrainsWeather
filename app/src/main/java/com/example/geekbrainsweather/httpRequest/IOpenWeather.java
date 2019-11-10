package com.example.geekbrainsweather.httpRequest;

import com.example.geekbrainsweather.httpRequest.currentWeatherEntities.WeatherCurrentRequest;
import com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities.ThreeHoursForecastRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IOpenWeather {
    @GET("data/2.5/weather")
    Call<WeatherCurrentRequest> loadCurrentWeather(@Query("q") String city,
                                                   @Query("appid") String keyApi,
                                                   @Query("units") String units);
    @GET("data/2.5/forecast")
    Call<ThreeHoursForecastRequest> loadForecastWeather(@Query("q") String city,
                                                        @Query("appid") String keyApi,
                                                        @Query("units") String units);

}
