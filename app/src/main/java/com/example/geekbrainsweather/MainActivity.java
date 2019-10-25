package com.example.geekbrainsweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.geekbrainsweather.fragments.EnterCityName;
import com.example.geekbrainsweather.fragments.SeveralPeriodsForecast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawer;
    Toolbar toolbar;
//    private TextInputEditText enteredCityName;
//    private MaterialButton sendQueryButton;
    private FloatingActionButton floatingActionButton;
    String cityName;

    EnterCityName enterCityNameFragment;
    SeveralPeriodsForecast severalPeriodsForecast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragments();
        setDefaultFragment();
        initViews();
        initSideMenu();
        setFloatingActionButtonBehaviour();
        //setSendQueryButtonBehaviour();
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* receiveDataFromFragment();
        sendCityNameToFragment();
        setSeveralPeriodsForecastFragment();*/
    }

    /*private void receiveDataFromFragment() {
        Intent intent = getIntent();
        cityName = intent.getStringExtra("enteredCityName");
    }

    private void sendCityNameToFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("cityName", cityName);
        severalPeriodsForecast = new SeveralPeriodsForecast();
        severalPeriodsForecast.setArguments(bundle);
    }*/

    /*private void setSendQueryButtonBehaviour() {
        sendQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                String cityName = Objects.requireNonNull(enteredCityName.getText()).toString();
                intent.putExtra("cityValue", cityName);
                setResult(RESULT_OK, intent);

                startActivity(intent);
            }
        });
    }*/

    private void initFragments() {
        enterCityNameFragment = new EnterCityName();
    }

    private void setDefaultFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, enterCityNameFragment)
                .addToBackStack(null)
                .commit();
    }

    /*private void setSeveralPeriodsForecastFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, severalPeriodsForecast)
                .commit();
    }*/

    private void initViews() {
        floatingActionButton = findViewById(R.id.floatingActionButton2);
//        enteredCityName = findViewById(R.id.enteredCityName);
//        sendQueryButton = findViewById(R.id.checkWeatherButton);
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

    private void initSideMenu() {
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.nav_home) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
