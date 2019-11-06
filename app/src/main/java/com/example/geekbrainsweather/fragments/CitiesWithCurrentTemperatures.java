package com.example.geekbrainsweather.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.geekbrainsweather.CityWithCurrentTemperatureItem;
import com.example.geekbrainsweather.CityWithCurrentTemperatureRecyclerViewAdapter;
import com.example.geekbrainsweather.ParseJsonToCityWithCurrentTemperatureItem;
import com.example.geekbrainsweather.R;
import com.example.geekbrainsweather.SwipeToDeleteCallback;

import java.util.ArrayList;
import java.util.Objects;

public class CitiesWithCurrentTemperatures
        extends Fragment
        implements CityWithCurrentTemperatureRecyclerViewAdapter.OnCityClickListener {

    private RecyclerView recyclerView;
    private ArrayList<CityWithCurrentTemperatureItem> listOfCities = new ArrayList<>();
    private CityWithCurrentTemperatureRecyclerViewAdapter adapter;
    private final String preferenceKey = "listOfSavedCities";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle("GeekBrainsWeather");
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_cities_with_current_temperatures, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        initRecyclerView(listOfCities);

        final SharedPreferences loadedCitiesList = Objects.requireNonNull(getActivity()).getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        if (loadedCitiesList.getAll().size() == 0) {
            initDefaultCity();
        } else {
            loadListOfCitiesFromFile(loadedCitiesList);
        }
    }

    // TODO: 2019-11-04 add city search bar with RecyclerView item highlight
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_save) {
            final SharedPreferences savedCitiesList = Objects.requireNonNull(getActivity()).getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
            Toast.makeText(getContext(),"Current cities saved.", Toast.LENGTH_LONG).show();
            saveCurrentListToFile(savedCitiesList);
        }
        return true;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_cities_with_current_temperatures);
    }

    private void initRecyclerView(ArrayList<CityWithCurrentTemperatureItem> listOfCities) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getBaseContext());
        adapter = new CityWithCurrentTemperatureRecyclerViewAdapter(getActivity(), listOfCities, this);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void initDefaultCity() {
        String defaultCity = "Moscow";
        if (listNotContains(defaultCity)) {
            new AsyncAddNewCityToList().execute(defaultCity);
        }
    }

    private void loadListOfCitiesFromFile(SharedPreferences preferences) {
        for (int i = 0; i < preferences.getAll().size(); i++) {
            new AsyncAddNewCityToList().execute(preferences.getString(Integer.toString(i), "Wrong City Name"));
        }
    }

    // TODO: 2019-11-06 change to retrofit and cancel screenOrientation lock
    @SuppressLint("StaticFieldLeak")
    private class AsyncAddNewCityToList extends AsyncTask<String, Void, CityWithCurrentTemperatureItem> {
        @Override
        protected CityWithCurrentTemperatureItem doInBackground(String... strings) {
            return getCityFromJson(strings[0]);
        }

        @Override
        protected void onPostExecute(CityWithCurrentTemperatureItem cityWithCurrentTemperatureItem) {
            if (cityWithCurrentTemperatureItem.getCityName().equals("Wrong City Name")) {
                Toast.makeText(getContext(), "Wrong City Name", Toast.LENGTH_LONG).show();
            } else if (listNotContains(cityWithCurrentTemperatureItem.getCityName())) {
                listOfCities.add(cityWithCurrentTemperatureItem);
                adapter.notifyItemInserted(listOfCities.size());
            }
        }
    }

    private boolean listNotContains(String cityName) {
        for (CityWithCurrentTemperatureItem itemInList : listOfCities) {
            if (itemInList.getCityName().toLowerCase().equals(cityName.toLowerCase())) {
                return false;
            }
        }
        return true;
    }

    private CityWithCurrentTemperatureItem getCityFromJson(String cityName) {
        ParseJsonToCityWithCurrentTemperatureItem parser = new ParseJsonToCityWithCurrentTemperatureItem(getContext(), cityName);
        return parser.getParsedCityWithCurrentTemperatureItem();
    }

    public void addNewCity() {
        final String newCity = Objects.requireNonNull(getArguments()).getString("newCity");
        if (listNotContains(newCity)) {
            new AsyncAddNewCityToList().execute(newCity);
        } else {
            Toast.makeText(getContext(), newCity + " is already in list", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCityClick(int position) {
        String cityName = listOfCities.get(position).getCityName();
        Bundle bundle = new Bundle();
        bundle.putString("cityName", cityName);
        SeveralPeriodsForecast severalPeriodsForecast = new SeveralPeriodsForecast();
        severalPeriodsForecast.setArguments(bundle);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, severalPeriodsForecast)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onDestroyView() {
        final SharedPreferences savedCitiesList = Objects.requireNonNull(getActivity()).getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        saveCurrentListToFile(savedCitiesList);
        super.onDestroyView();
    }

    private void saveCurrentListToFile(SharedPreferences preferences) {
        SharedPreferences.Editor editor = preferences.edit();

        for (int i = 0; i < listOfCities.size(); i++) {
            editor.putString(Integer.toString(i), listOfCities.get(i).getCityName());
        }
        editor.apply();
    }
}