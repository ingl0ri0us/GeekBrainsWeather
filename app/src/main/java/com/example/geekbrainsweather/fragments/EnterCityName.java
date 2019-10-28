package com.example.geekbrainsweather.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geekbrainsweather.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class EnterCityName extends Fragment {

    private TextInputEditText enteredCityName;
    private MaterialButton sendQueryButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_enter_city_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        setSendQueryButtonBehavior();
    }

    private void initViews(View view) {
        enteredCityName = view.findViewById(R.id.enteredCityName_fragment);
        sendQueryButton = view.findViewById(R.id.checkWeatherButton_fragment);
    }

    private void setSendQueryButtonBehavior() {
        sendQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = Objects.requireNonNull(enteredCityName.getText()).toString();
                Bundle bundle = new Bundle();
                bundle.putString("cityName", cityName);
                SeveralPeriodsForecast severalPeriodsForecast = new SeveralPeriodsForecast();
                severalPeriodsForecast.setArguments(bundle);
                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, severalPeriodsForecast)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
