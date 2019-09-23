package com.example.geekbrainsweather;

import android.content.Context;

import java.util.Date;

public class GreetingsBuilder {
    String getGreetings(Context context) {
        int currentHour = new Date(System.currentTimeMillis()).getHours();
        String result;

        if (5 <= currentHour && currentHour < 12) {
            result = context.getString(R.string.good_morning);
        } else if(12 <= currentHour && currentHour < 18) {
            result = context.getString(R.string.good_afternoon);
        } else if(18 <= currentHour && currentHour < 21) {
            result = context.getString(R.string.good_evening);
        } else {
            result = context.getString(R.string.good_night);
        }

        return result;
    }
}
