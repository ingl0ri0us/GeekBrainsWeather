package com.example.geekbrainsweather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJsonToCityWithCurrentTemperatureItem {
    private JSONObject jsonObject;
    private CityWithCurrentTemperatureItem parsedItem;
    private Context context;

    public ParseJsonToCityWithCurrentTemperatureItem(Context context, String cityName) {
        this.context = context;
        String FORECAST_INTERVAL = "current";
        jsonObject = JSONDownloader.getJSONObject(cityName, FORECAST_INTERVAL);
    }

    public CityWithCurrentTemperatureItem getParsedCityWithCurrentTemperatureItem() {
        if(jsonObject == null) {
            parsedItem = new CityWithCurrentTemperatureItem(ContextCompat.getDrawable(context, R.drawable.alert_circle_dark),
                    "Wrong City Name",
                    "");
        } else {
            try {
                parsedItem = getDataFromJson();
            } catch (JSONException e) {
                Log.e("getForecastPeriod", "JSONException");
                e.printStackTrace();
            }
        }
        return parsedItem;
    }

    private CityWithCurrentTemperatureItem getDataFromJson() throws JSONException {
        JSONArray forecastData = jsonObject.getJSONArray("weather");

        long currentTimeFromJson = jsonObject.getLong("dt") * 1000;
        long sunrise = jsonObject.getJSONObject("sys").getLong("sunrise") * 1000;
        long sunset = jsonObject.getJSONObject("sys").getLong("sunset") * 1000;

        Drawable icon = getWeatherIcon(forecastData.getJSONObject(0),currentTimeFromJson, sunrise, sunset);
        String cityNameFromJson = jsonObject.getString("name");
        String temperatureFromJson = getTemperature(jsonObject.getJSONObject("main")) + " ℃";

        return new CityWithCurrentTemperatureItem(icon, cityNameFromJson, temperatureFromJson);
    }

    private String getTemperature(JSONObject jsonObject) {
        int temperatureValue = -999;
        try {
            temperatureValue = jsonObject.getInt("temp");
        } catch (JSONException e) {
            Log.e("getTemperature", "JSONException");
            e.printStackTrace();
        }
        return Integer.toString(temperatureValue);
    }

    private Drawable getWeatherIcon(JSONObject jsonObject, long currentTimeFromJson, long sunrise, long sunset) {
        int iconIdFromJson = -1;
        try {
            iconIdFromJson = jsonObject.getInt("id");
        } catch (JSONException e) {
            Log.e("getWeatherIcon", "JSONException");
            e.printStackTrace();
        }
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
