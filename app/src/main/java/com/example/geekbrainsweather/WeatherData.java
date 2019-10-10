package com.example.geekbrainsweather;

public class WeatherData {
    private static String[] moscow3dayForecast = new String[]{"+14 ℃", "+13 ℃", "+12 ℃"};
    private static String[] london3dayForecast = new String[]{"+9 ℃", "+12 ℃", "+15 ℃"};
    private static String[] paris3dayForecast = new String[]{"+29 ℃", "+33 ℃", "+34 ℃"};

    static public String[] get3dayForecastById(int id) {
        if (id == 0) {
            return moscow3dayForecast;
        } else if (id == 1) {
            return london3dayForecast;
        } else {
            return paris3dayForecast;
        }
    }
}
