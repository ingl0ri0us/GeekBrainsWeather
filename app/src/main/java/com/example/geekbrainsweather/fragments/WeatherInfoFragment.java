package com.example.geekbrainsweather.fragments;

import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.geekbrainsweather.R;

import java.util.Objects;

public class WeatherInfoFragment extends Fragment {
    static WeatherInfoFragment create(int index) {
        WeatherInfoFragment fragment = new WeatherInfoFragment();

        Bundle args = new Bundle();
        args.putInt("index", index);
        fragment.setArguments(args);
        return fragment;
    }

    int getIndex() {
        return Objects.requireNonNull(getArguments()).getInt("index", 0);
    }

    @Nullable
    @Override
    @SuppressLint("Recycle")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        TextView weatherInfo = new TextView(getActivity());
        TypedArray weatherInfoArray = getResources().obtainTypedArray(R.array.cities_temperatures);
        weatherInfo.setText(weatherInfoArray.getResourceId(getIndex(), -1));

        return weatherInfo;
    }
}
