package com.example.geekbrainsweather.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geekbrainsweather.R;

import java.util.Objects;

public class SensorTemperature extends Fragment {

    private TextView temperatureValue;
    private TextView humidityValue;
    private SensorManager sensorManager;
    private Sensor temperatureSensor;
    private Sensor humiditySensor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor_temperature, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initViews(view);
        getSensors();
        sensorManager.registerListener(temperatureListener, temperatureSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(humidityListener, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews(View view) {
        temperatureValue = view.findViewById(R.id.temperature_sensor_value);
        humidityValue = view.findViewById(R.id.humidity_sensor_value);
    }

    private void getSensors() {
        sensorManager = (SensorManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            temperatureSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        } else {
            temperatureValue.setText(R.string.no_sensor);
        }
        if (sensorManager != null) {
            humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);
        } else {
            humidityValue.setText(R.string.no_sensor);
        }
    }

    private SensorEventListener temperatureListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            showTemperatureValue(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @SuppressLint("SetTextI18n")
    private void showTemperatureValue(SensorEvent event) {
        temperatureValue.setText(Float.toString(event.values[0]));
    }

    private SensorEventListener humidityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            showHumidityValue(event);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @SuppressLint("SetTextI18n")
    private void showHumidityValue(SensorEvent event) {
        humidityValue.setText(Float.toString(event.values[0]));
    }
}
