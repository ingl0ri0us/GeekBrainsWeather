package com.example.geekbrainsweather;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class JSONDownloader {
    private static final String OPEN_WEATHER_API_KEY = "35c744e8949e353ebb9961393941c850";
    private static final String KEY = "x-api-key";
    private static final String RESPONSE = "cod";
    private static final int ALL_GOOD = 200;

    static JSONObject getJSONObject(String city, String urlKey) {
        try {
            URL url = new URL(String.format(getProperApiUrl(urlKey), city));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty(KEY, OPEN_WEATHER_API_KEY);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder rawData = new StringBuilder(1024);
            String tempVariable;

            while ((tempVariable = reader.readLine()) != null) {
                rawData.append(tempVariable).append("\n");
            }

            reader.close();

            JSONObject jsonObject = new JSONObject(rawData.toString());
            if (jsonObject.getInt(RESPONSE) == ALL_GOOD) {
                return jsonObject;
            } else {
                return null;
            }
        } catch (Exception exc) {
            exc.printStackTrace();
            return null;
        }
    }

    private static String getProperApiUrl(String urlKey) {
        if(urlKey.equals("threeHours")) {
            return "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric";
        } else {
            return "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
        }
    }
}
