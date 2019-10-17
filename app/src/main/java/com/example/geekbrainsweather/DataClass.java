package com.example.geekbrainsweather;

import android.graphics.drawable.Drawable;

import java.util.Calendar;
import java.util.Locale;

class DataClass {
    Drawable weatherThumbnail;
    String weekDay;
    String temperatureValue;
    String humidityValue, airPressureValue, windSpeedValue;

    DataClass(Drawable weatherThumbnail, String weekDay, String temperatureValue, String humidityValue, String airPressureValue, String windSpeedValue) {
        this.weatherThumbnail = weatherThumbnail;
        this.weekDay = weekDay;
        this.temperatureValue = temperatureValue;
        this.humidityValue = humidityValue;
        this.airPressureValue = airPressureValue;
        this.windSpeedValue = windSpeedValue;
    }

    static String setProperWeekDay(int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, dayIndex);

        return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
    }
}
