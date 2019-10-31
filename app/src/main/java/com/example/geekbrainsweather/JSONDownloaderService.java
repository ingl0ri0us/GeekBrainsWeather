package com.example.geekbrainsweather;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JSONDownloaderService extends IntentService {

    private static final String OPEN_WEATHER_API_KEY = "35c744e8949e353ebb9961393941c850";
    private static final String KEY = "x-api-key";

    public JSONDownloaderService() {
        super("JSONDownloaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String cityName = intent.getStringExtra("cityName");
        String forecastInterval = intent.getStringExtra("forecastInterval");
        publishResult(getJsonData(cityName, forecastInterval));
    }

    private void publishResult(String JsonData) {
        Intent intent = new Intent(MainActivity.BROADCAST_ACTION);
        intent.putExtra("JsonData", JsonData);
        sendBroadcast(intent);
    }

    private String getJsonData(String city, String urlKey) {
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

            return rawData.toString();

        } catch (IOException exc) {
            Log.e("JsonData()", "IOException");
            return null;
        }
    }

    private static String getProperApiUrl(String urlKey) {
        if (urlKey.equals("threeHours")) {
            return "https://api.openweathermap.org/data/2.5/forecast?q=%s&units=metric";
        } else {
            return "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";
        }
    }
}
