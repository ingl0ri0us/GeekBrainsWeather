package com.example.geekbrainsweather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.geekbrainsweather.database.CityWithCurrentTempTable;
import com.example.geekbrainsweather.httpRequest.currentWeatherEntities.MainCurrentRestModel;
import com.example.geekbrainsweather.httpRequest.currentWeatherEntities.WeatherCurrentRequest;
import com.example.geekbrainsweather.httpRequest.currentWeatherEntities.WeatherCurrentRestModel;

public class CityWithCurrentTemperatureParser {
    private WeatherCurrentRequest mainModel;
    private Context context;
    private int thumbnailId;
    private String cityName;
    private int temperatureValue;
    private String temperatureAsString;
    private Drawable weatherThumbnail;

    public CityWithCurrentTemperatureParser(WeatherCurrentRequest mainModel, Context context) {
        this.mainModel = mainModel;
        this.context = context;
        parseJsonToVariables();
    }

    private void parseJsonToVariables() {
        long currentTimeFromJson = mainModel.dt * 1000;
        long sunrise = mainModel.sys.sunrise * 1000;
        long sunset = mainModel.sys.sunset * 1000;

        weatherThumbnail = getWeatherIconFromJson(mainModel.weather[0], currentTimeFromJson, sunrise, sunset);
        thumbnailId = getThumbnailId(mainModel.weather[0], currentTimeFromJson, sunrise, sunset);
        cityName = getCityName();
        temperatureValue = getTemperatureAsInt(mainModel.main);
        temperatureAsString = getTemperatureAsString(mainModel.main);
    }

    public CityWithCurrentTemperatureItem getItemParsedFromJson() {
        return new CityWithCurrentTemperatureItem(weatherThumbnail, cityName, temperatureAsString);
    }

    public void saveItemToDataBase(SQLiteDatabase database) {
        CityWithCurrentTempTable.addCityWithCurrentTempAndTimeToDataBase(
                cityName.toLowerCase(),
                thumbnailId,
                temperatureValue,
                System.currentTimeMillis(),
                database
        );
    }

    private String getCityName() {
        return mainModel.name;
    }

    private int getTemperatureAsInt(MainCurrentRestModel model) {
        return (int) model.temp;
    }

    private String getTemperatureAsString(MainCurrentRestModel model) {
        return (int) model.temp + " ℃";
    }

    private int getThumbnailId(WeatherCurrentRestModel model, long currentTimeFromJson, long sunrise, long sunset) {
        int iconIdFromJson = model.id;

        int id = iconIdFromJson / 100;

        int iconId;

        if (iconIdFromJson == 800) {
            if (currentTimeFromJson >= sunrise && currentTimeFromJson < sunset) {
                iconId = 1;
            } else {
                iconId = 4;
            }
        } else {
            switch (id) {
                case 2: {
                    iconId = 2;
                    break;
                }
                case 3:
                case 5: {
                    iconId = 5;
                    break;
                }
                case 6: {
                    iconId = 6;
                    break;
                }
                case 7: {
                    iconId = 7;
                    break;
                }
                case 8: {
                    iconId = 8;
                    break;
                }
                default: {
                    iconId = 9;
                    break;
                }
            }
        }
        return iconId;
    }

    private Drawable getWeatherIconFromJson(WeatherCurrentRestModel model, long currentTimeFromJson, long sunrise, long sunset) {
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
