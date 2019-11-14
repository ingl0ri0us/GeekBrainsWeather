package com.example.geekbrainsweather.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.geekbrainsweather.CityWithCurrentTemperatureItem;
import com.example.geekbrainsweather.CityWithCurrentTemperatureParser;
import com.example.geekbrainsweather.CityWithCurrentTemperatureRecyclerViewAdapter;
import com.example.geekbrainsweather.R;
import com.example.geekbrainsweather.SwipeToDeleteCallback;
import com.example.geekbrainsweather.database.CityWithCurrentTempTable;
import com.example.geekbrainsweather.database.DatabaseHelper;
import com.example.geekbrainsweather.httpRequest.OpenWeatherRepo;
import com.example.geekbrainsweather.httpRequest.currentWeatherEntities.WeatherCurrentRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CitiesWithCurrentTemperatures
        extends Fragment
        implements CityWithCurrentTemperatureRecyclerViewAdapter.OnCityClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private ArrayList<CityWithCurrentTemperatureItem> listOfCities = new ArrayList<>();
    private CityWithCurrentTemperatureRecyclerViewAdapter adapter;
    private final String preferenceKey = "listOfSavedCities";
    private SQLiteDatabase database;

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
        initDB();
        setFloatingActionButtonBehaviour();
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
        if (id == R.id.menu_save) {
            final SharedPreferences savedCitiesList = Objects.requireNonNull(getActivity()).getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
            Toast.makeText(getContext(), "Current cities saved.", Toast.LENGTH_LONG).show();
            saveCurrentListToFile(savedCitiesList);
        }
        return true;
    }

    private void initDB() {
        database = new DatabaseHelper(getContext()).getWritableDatabase();
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerView_cities_with_current_temperatures);
        floatingActionButton = view.findViewById(R.id.floatingActionButton);
    }

    private void setFloatingActionButtonBehaviour() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInputDialog();
            }
        });
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setTitle("Enter city name");

        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addNewCity(input.getText().toString());
            }
        });
        builder.show();
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
            addCityToList(defaultCity);
        }
    }

    private void loadListOfCitiesFromFile(SharedPreferences preferences) {
        for (int i = 0; i < preferences.getAll().size(); i++) {
            String loadedCityName = preferences.getString(Integer.toString(i), "");
            if (listNotContains(loadedCityName)) {
                addCityToList(loadedCityName);
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

    private void addNewCity(String newCity) {
        if (listNotContains(newCity)) {
            addCityToList(newCity);
        } else {
            Toast.makeText(getContext(), newCity + " is already in list", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCityClick(int position) {
        String cityName = listOfCities.get(position).getCityName();
        Bundle bundle = new Bundle();
        bundle.putString("cityName", cityName);
        ThreeHoursForecast threeHoursForecast = new ThreeHoursForecast();
        threeHoursForecast.setArguments(bundle);
        Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, threeHoursForecast)
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

    private void addCityToList(String cityName) {
        String API_KEY = "35c744e8949e353ebb9961393941c850";
        String MEASURE_UNITS = "metric";
        OpenWeatherRepo
                .getSingleton()
                .getAPI()
                .loadCurrentWeather(cityName, API_KEY, MEASURE_UNITS)
                .enqueue(new Callback<WeatherCurrentRequest>() {
                    @Override
                    public void onResponse(@NonNull Call<WeatherCurrentRequest> call, @NonNull Response<WeatherCurrentRequest> response) {
                        if (response.body() != null && response.isSuccessful()) {
                            WeatherCurrentRequest mainModel = response.body();
                            Context context = getContext();
                            CityWithCurrentTemperatureParser parser = new CityWithCurrentTemperatureParser(mainModel, context);
                            parser.saveItemToDataBase(database);
                            //CityWithCurrentTempTable.deleteAll(database);
                            CityWithCurrentTemperatureItem item = parser.getItemParsedFromJson();
                            listOfCities.add(item);
                            adapter.notifyItemInserted(listOfCities.size());
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WeatherCurrentRequest> call, @NonNull Throwable t) {
                        Toast.makeText(getContext(), "Wrong City Name", Toast.LENGTH_LONG).show();
                    }
                });
    }
}