package com.example.geekbrainsweather;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SecondActivity extends AppCompatActivity {

    private String city;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        readDataFromIntent();
        initViews();
        setTitle(city);
        initRecyclerView(city);

    }

    private void readDataFromIntent() {
        city = getIntent().getStringExtra("cityValue");
    }

    private void initViews() {

        recyclerView = findViewById(R.id.recyclerView);
    }

    private DataClass[] getForecastArray(String cityName) {
        DataClass[] moscow3dayForecast = new DataClass[]{
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_cloudy_dark),
                        DataClass.setProperWeekDay(0),
                        "+25",
                        "75%",
                        "1050 hPa",
                        "15 m/sec"),
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_fog_dark),
                        DataClass.setProperWeekDay(1),
                        "+24",
                        "74%",
                        "1000 hPa",
                        "10 m/sec"),
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_lightning_dark),
                        DataClass.setProperWeekDay(2),
                        "+23",
                        "73%",
                        "1150 hPa",
                        "12 m/sec"),
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_pouring_dark),
                        DataClass.setProperWeekDay(3),
                        "+22",
                        "72%",
                        "1022 hPa",
                        "2 m/sec")
        };
        DataClass[] london3dayForecast = new DataClass[]{
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_sunny_dark),
                        DataClass.setProperWeekDay(0),
                        "+15",
                        "75%",
                        "1050 hPa",
                        "15 m/sec"),
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_pouring_dark),
                        DataClass.setProperWeekDay(1),
                        "+16",
                        "65%",
                        "1000 hPa",
                        "10 m/sec"),
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_lightning_dark),
                        DataClass.setProperWeekDay(2),
                        "+17",
                        "70%",
                        "1150 hPa",
                        "12 m/sec"),
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_fog_dark),
                        DataClass.setProperWeekDay(3),
                        "+18",
                        "72%",
                        "1022 hPa",
                        "2 m/sec")
        };
        DataClass[] paris3dayForecast = new DataClass[]{
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_pouring_dark),
                        DataClass.setProperWeekDay(0),
                        "+30",
                        "75%",
                        "1050 hPa",
                        "15 m/sec"),
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_sunny_dark),
                        DataClass.setProperWeekDay(1),
                        "+28",
                        "65%",
                        "1000 hPa",
                        "10 m/sec"),
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_cloudy_dark),
                        DataClass.setProperWeekDay(2),
                        "+26",
                        "70%",
                        "1150 hPa",
                        "12 m/sec"),
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.weather_lightning_dark),
                        DataClass.setProperWeekDay(3),
                        "+24",
                        "72%",
                        "1022 hPa",
                        "2 m/sec")
        };
        DataClass[] noSuchCity = new DataClass[]{
                new DataClass(ContextCompat.getDrawable(getApplicationContext(), R.drawable.alert_circle_dark),
                        "No Such City",
                        "",
                        "",
                        "",
                        "")
        };

        switch (cityName) {
            case "Москва":
            case "Moscow":
                return moscow3dayForecast;
            case "Лондон":
            case "London":
                return london3dayForecast;
            case "Париж":
            case "Paris":
                return paris3dayForecast;
            default:
                return noSuchCity;
        }
    }

    private void initRecyclerView(String cityName) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext());
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getForecastArray(cityName));

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}

