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

import com.example.geekbrainsweather.CityWithCurrentTemperatureItem;
import com.example.geekbrainsweather.CityWithCurrentTemperatureRecyclerViewAdapter;
import com.example.geekbrainsweather.ParseJsonToCityWithCurrentTemperatureItem;
import com.example.geekbrainsweather.R;

import java.util.ArrayList;
import java.util.Objects;

public class CitiesWithCurrentTemperatures extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<CityWithCurrentTemperatureItem> listOfCities = new ArrayList<>();
    private CityWithCurrentTemperatureRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cities_with_current_temperatures, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);

        initDefaultCities();
        initRecyclerView(listOfCities);

    }

    /*@SuppressLint("StaticFieldLeak")
    private class AsyncFetchCitiesWithCurrentTemp extends AsyncTask<Void, Void, ArrayList<CityWithCurrentTemperatureItem>> {
        @Override
        protected ArrayList<CityWithCurrentTemperatureItem> doInBackground(Void... voids) {
            return initDefaultCities();
        }
        @Override
        protected void onPostExecute(ArrayList<CityWithCurrentTemperatureItem> cities) {
            initRecyclerView(cities);
        }
    }*/

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_cities_with_current_temperatures);
    }

    private ArrayList<CityWithCurrentTemperatureItem> initDefaultCities() {
        listOfCities.add(getCityFromJson("Moscow"));
        listOfCities.add(getCityFromJson("London"));
        listOfCities.add(getCityFromJson("Paris"));
        return listOfCities;
    }

    private CityWithCurrentTemperatureItem getCityFromJson(String cityName) {
        ParseJsonToCityWithCurrentTemperatureItem parser = new ParseJsonToCityWithCurrentTemperatureItem(getActivity(), getContext(), cityName);
        return parser.getParsedCityWithCurrentTemperatureItem();
    }

    private void initRecyclerView(ArrayList<CityWithCurrentTemperatureItem> listOfCities) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getBaseContext());
        adapter = new CityWithCurrentTemperatureRecyclerViewAdapter(listOfCities);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    public void addNewCity() {
        final String newCity = Objects.requireNonNull(getArguments()).getString("newCity");
        new AsyncAddNewCityToList().execute(newCity);
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncAddNewCityToList extends AsyncTask<String, Void, CityWithCurrentTemperatureItem> {
        @Override
        protected CityWithCurrentTemperatureItem doInBackground(String... strings) {
            return getCityFromJson(strings[0]);
        }

        @Override
        protected void onPostExecute(CityWithCurrentTemperatureItem cityWithCurrentTemperatureItem) {
            listOfCities.add(cityWithCurrentTemperatureItem);
            adapter.notifyItemInserted(listOfCities.size());
        }
    }
}
