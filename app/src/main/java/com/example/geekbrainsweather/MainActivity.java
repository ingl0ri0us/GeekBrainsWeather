package com.example.geekbrainsweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.geekbrainsweather.fragments.About;
import com.example.geekbrainsweather.fragments.CitiesWithCurrentTemperatures;
import com.example.geekbrainsweather.fragments.SensorTemperature;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    String newCity;

    SensorTemperature sensorTemperatureFragment;
    CitiesWithCurrentTemperatures citiesWithCurrentTemperaturesFragment;
    About aboutFragment;

    // TODO: 2019-11-04 add landscape layout with current weather information

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        setDefaultFragment();
        initViews();
        initSideMenu();
        setFloatingActionButtonBehaviour();
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
        floatingActionButton = findViewById(R.id.floatingActionButton2);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter city name");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newCity = input.getText().toString();
                Bundle bundle = new Bundle();
                bundle.putString("newCity", newCity);
                citiesWithCurrentTemperaturesFragment.setArguments(bundle);
                citiesWithCurrentTemperaturesFragment.addNewCity();
            }
        });
        builder.show();
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
