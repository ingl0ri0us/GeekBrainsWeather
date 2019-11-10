package com.example.geekbrainsweather;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.geekbrainsweather.httpRequest.currentWeatherEntities.MainCurrentRestModel;
import com.example.geekbrainsweather.httpRequest.currentWeatherEntities.WeatherCurrentRequest;
import com.example.geekbrainsweather.httpRequest.currentWeatherEntities.WeatherCurrentRestModel;

public class CityWithCurrentTemperatureParser {
    private WeatherCurrentRequest mainModel;
    private Context context;

    public CityWithCurrentTemperatureParser(WeatherCurrentRequest mainModel, Context context) {
        this.mainModel = mainModel;
        this.context = context;
    }

    public CityWithCurrentTemperatureItem getItem() {
        long currentTimeFromJson = mainModel.dt * 1000;
        long sunrise = mainModel.sys.sunrise * 1000;
        long sunset = mainModel.sys.sunset * 1000;

        Drawable weatherThumbnail = getWeatherIcon(mainModel.weather[0], currentTimeFromJson, sunrise, sunset);
        String cityName = mainModel.name;
        String currentTemperature = getTemperature(mainModel.main) + " ℃";

        return new CityWithCurrentTemperatureItem(weatherThumbnail, cityName, currentTemperature);
    }
    private String getTemperature(MainCurrentRestModel model) {
        return Integer.toString((int) model.temp);
    }

    private Drawable getWeatherIcon(WeatherCurrentRestModel model, long currentTimeFromJson, long sunrise, long sunset) {
        int iconIdFromJson = model.id;

        int id = iconIdFromJson / 100;

        Drawable iconToReturn;

        if (iconIdFromJson == 800) {
            if (currentTimeFromJson >= sunrise && currentTimeFromJson < sunset) {
                iconToReturn = ContextCompat.getDrawable(context, R.drawable.weather_sunny_dark);
            } else {
                iconToReturn = ContextCompat.getDrawable(context, R.drawable.weather_clear_night_dark);
            }
        } else {
            switch (id) {
                case 2: {
                    iconToReturn = ContextCompat.getDrawable(context, R.drawable.weather_lightning_dark);
                    break;
                }
                case 3:
                case 5: {
                    iconToReturn = ContextCompat.getDrawable(context, R.drawable.weather_pouring_dark);
                    break;
                }
                case 6: {
                    iconToReturn = ContextCompat.getDrawable(context, R.drawable.snowflake_dark);
                    break;
                }
                case 7: {
                    iconToReturn = ContextCompat.getDrawable(context, R.drawable.weather_fog_dark);
                    break;
                }
                case 8: {
                    iconToReturn = ContextCompat.getDrawable(context, R.drawable.weather_partly_cloudy_dark);
                    break;
                }
                default: {
                    iconToReturn = ContextCompat.getDrawable(context, R.drawable.alert_circle_dark);
                    break;
                }
            }
        }
        return iconToReturn;
    }
}
