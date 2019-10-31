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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.geekbrainsweather.fragments.About;
import com.example.geekbrainsweather.fragments.CitiesWithCurrentTemperatures;
import com.example.geekbrainsweather.fragments.EnterCityName;
import com.example.geekbrainsweather.fragments.SensorTemperature;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String BROADCAST_ACTION = "com.example.geekbrainsweather.sendingaction";

    DrawerLayout drawer;
    Toolbar toolbar;
    private FloatingActionButton floatingActionButton;

    String newCity;

    EnterCityName enterCityNameFragment;
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
        setFloatingActionButtonBehaviour();
    }

    private void initFragments() {
        enterCityNameFragment = new EnterCityName();
        sensorTemperatureFragment = new SensorTemperature();
        citiesWithCurrentTemperaturesFragment = new CitiesWithCurrentTemperatures();
        aboutFragment = new About();
    }

    private void setDefaultFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, enterCityNameFragment)
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
                Snackbar.make(v, getString(R.string.created_by), Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_add) {
            showInputDialog();
        }
        return true;
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert dialog");

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

        if (id == R.id.three_hour_forecast) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, enterCityNameFragment)
                    .commit();
        } else if (id == R.id.sensor_temperature) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, sensorTemperatureFragment)
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.list_of_cities) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, citiesWithCurrentTemperaturesFragment)
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
