package com.example.geekbrainsweather;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities.ForecastPeriodData;
import com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities.MainForecastRestModel;
import com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities.ThreeHoursForecastRequest;
import com.example.geekbrainsweather.httpRequest.threeHoursForecastEntities.WeatherForecastRestModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class ThreeHoursForecastParser {
    private ThreeHoursForecastRequest mainModel;
    private Context context;

    public ThreeHoursForecastParser(ThreeHoursForecastRequest mainModel, Context context) {
        this.mainModel = mainModel;
        this.context = context;
    }

    public ThreeHoursForecastItem[] getForecastDataArray() {
        int amountOfForecastPeriods = mainModel.amountOfForecastPeriods;
        long sunrise = mainModel.cityInfoData.sunrise;
        long sunset = mainModel.cityInfoData.sunset;

        ThreeHoursForecastItem[] forecastDataArray = new ThreeHoursForecastItem[amountOfForecastPeriods];

        for (int i = 0; i < amountOfForecastPeriods; i++) {
            ForecastPeriodData forecastPeriodModel = mainModel.listOfForecastPeriods[i];
            WeatherForecastRestModel weatherForecastRestModel = forecastPeriodModel.weatherForecastRestModels[0];
            MainForecastRestModel mainForecastRestModel = forecastPeriodModel.mainForecastRestModel;
            long timeOfForecastPeriod = forecastPeriodModel.dt * 1000;

            Drawable icon = getWeatherIcon(weatherForecastRestModel.id, timeOfForecastPeriod, sunrise, sunset);
            String forecastPeriod = getForecastPeriod(timeOfForecastPeriod);
            String temperatureValue = getTemperatureValue(mainForecastRestModel.temperature) + " ℃";
            String humidityValue = getHumidity(mainForecastRestModel.humidity) + " %";
            String pressureValue = getPressure(mainForecastRestModel.pressure) + " hPa";
            String windSpeedValue = getWindSpeed(forecastPeriodModel.windForecastRestModel.speed) + " m/sec";

            forecastDataArray[i] = new ThreeHoursForecastItem(icon, forecastPeriod, temperatureValue, humidityValue, pressureValue, windSpeedValue);
        }
        return forecastDataArray;
    }

    private String getWindSpeed(float speed) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(speed);
    }

    private String getPressure(int pressure) {
        return Integer.toString(pressure);
    }

    private String getHumidity(int humidity) {
        return Integer.toString(humidity);
    }

    private String getTemperatureValue(float temperature) {
        return Integer.toString((int) temperature);
    }

    private String getForecastPeriod(long timeOfForecastPeriod) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM HH:mm", Locale.getDefault());
        Date date = new Date(timeOfForecastPeriod);
        return dateFormat.format(date);
    }

    private Drawable getWeatherIcon(int iconIdFromJson, long timeOfForecastPeriod, long sunrise, long sunset) {
        int id = iconIdFromJson / 100;

        Drawable iconToReturn;

        if (iconIdFromJson == 800) {
            if (timeOfForecastPeriod >= sunrise && timeOfForecastPeriod < sunset) {
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
