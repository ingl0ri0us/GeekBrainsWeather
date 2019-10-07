package com.example.geekbrainsweather.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.geekbrainsweather.R;
import com.example.geekbrainsweather.WeatherInfoActivity;

import java.util.Objects;

public class CitiesFragment extends Fragment {
    private ListView listView;
    private TextView emptyTextView;

    private int currentPosition = 0;
    private boolean isOrientationLandscape;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "Fragment onCreateView", Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initList();
    }

    private void initViews(View view) {
        listView = view.findViewById(R.id.cities_list_view);
        emptyTextView = view.findViewById(R.id.cities_list_empty_view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "Fragment onActivityCreated", Toast.LENGTH_SHORT).show();
        super.onActivityCreated(savedInstanceState);

        isOrientationLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("CurrentCity", 0);
        }
        if (isOrientationLandscape) {
            listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            showWeatherInfo();
        }
    }

    private void initList() {
        ArrayAdapter adapter =
                ArrayAdapter.createFromResource(
                        Objects.requireNonNull(getActivity()),
                        R.array.cities,
                        android.R.layout.simple_list_item_activated_1
                );

        listView.setAdapter(adapter);

        listView.setEmptyView(emptyTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition = position;
                showWeatherInfo();
            }
        });
    }

    private void showWeatherInfo() {
        if (isOrientationLandscape) {
            listView.setItemChecked(currentPosition, true);

            WeatherInfoFragment weatherInfo = (WeatherInfoFragment)
                    Objects.requireNonNull(getFragmentManager()).findFragmentById(R.id.city_weather);

            if (weatherInfo == null || weatherInfo.getIndex() != currentPosition) {
                weatherInfo = WeatherInfoFragment.create(currentPosition);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.city_weather, weatherInfo);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack("Some_Key");
                ft.commit();
            }
        } else {
            Intent intent = new Intent();
            intent.setClass(Objects.requireNonNull(getActivity()), WeatherInfoActivity.class);
            intent.putExtra("index", currentPosition);
            startActivity(intent);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        Toast.makeText(getContext(), "Fragment onAttach", Toast.LENGTH_SHORT).show();
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Toast.makeText(getContext(), "Fragment onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        Toast.makeText(getContext(), "Fragment onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    public void onStop() {
        Toast.makeText(getContext(), "Fragment onStop", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Toast.makeText(getContext(), "Fragment onDestroyView", Toast.LENGTH_SHORT).show();
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        Toast.makeText(getContext(), "Fragment onDetach", Toast.LENGTH_SHORT).show();
        super.onDetach();
    }
}
