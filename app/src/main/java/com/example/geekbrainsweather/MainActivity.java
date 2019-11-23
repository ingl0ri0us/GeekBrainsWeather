package com.example.geekbrainsweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.geekbrainsweather.fragments.About;
import com.example.geekbrainsweather.fragments.CitiesWithCurrentTemperatures;
import com.example.geekbrainsweather.fragments.SensorTemperature;
import com.google.android.material.navigation.NavigationView;

public class MainActivity
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;

    SensorTemperature sensorTemperatureFragment;
    CitiesWithCurrentTemperatures citiesWithCurrentTemperaturesFragment;
    About aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        setDefaultFragment();
        initViews();
        initSideMenu();
    }

    private void initFragments() {
        sensorTemperatureFragment = new SensorTemperature();
        citiesWithCurrentTemperaturesFragment = new CitiesWithCurrentTemperatures();
        aboutFragment = new About();
    }

    private void setDefaultFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, citiesWithCurrentTemperaturesFragment)
                .commit();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initSideMenu() {
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.sensor_temperature) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, sensorTemperatureFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.about) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, aboutFragment)
                    .addToBackStack(null)
                    .commit();
        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
