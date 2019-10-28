package com.example.geekbrainsweather.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.geekbrainsweather.ParseJsonToThreeHoursForecastArray;
import com.example.geekbrainsweather.ThreeHoursForecastItem;
import com.example.geekbrainsweather.R;
import com.example.geekbrainsweather.RecyclerViewAdapter;

import java.util.Objects;

public class SeveralPeriodsForecast extends Fragment {

    private String cityName;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        cityName = Objects.requireNonNull(getArguments()).getString("cityName");
        return inflater.inflate(R.layout.fragment_several_periods_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        new AsyncFetchFragment().execute();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_fragment);
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncFetchFragment extends AsyncTask<ThreeHoursForecastItem[], ThreeHoursForecastItem[], ThreeHoursForecastItem[]> {
        @Override
        protected ThreeHoursForecastItem[] doInBackground(ThreeHoursForecastItem[]... threeHoursForecastItems) {
            ParseJsonToThreeHoursForecastArray parser = new ParseJsonToThreeHoursForecastArray(getContext(), cityName);
            return parser.getForecastArray();
        }
        @Override
        protected void onPostExecute(ThreeHoursForecastItem[] threeHoursForecastItems) {
            initRecyclerView(threeHoursForecastItems);
        }
    }

    private void initRecyclerView(ThreeHoursForecastItem[] data) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getBaseContext());
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
