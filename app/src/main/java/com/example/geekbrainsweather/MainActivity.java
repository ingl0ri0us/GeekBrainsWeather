package com.example.geekbrainsweather;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText enteredCityName;
    private CheckBox checkBoxHumidity;
    private CheckBox checkBoxAirPressure;
    private CheckBox checkBoxWindSpeed;
    private Button sendQueryButton;
    private static final String ACTIVITY_STATE_TAG = "Activity State Change";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setSendQueryButtonBehaviour();

        Toast.makeText(getBaseContext(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    private void setSendQueryButtonBehaviour() {
        sendQueryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Activity2.class);
                String cityName = enteredCityName.getText().toString();
                intent.putExtra("cityValue", cityName);
                setResult(RESULT_OK, intent);
                if (checkBoxHumidity.isChecked()) {
                    intent.putExtra("humidityState", true);
                }
                if (checkBoxAirPressure.isChecked()) {
                    intent.putExtra("airPressureState", true);
                }
                if (checkBoxWindSpeed.isChecked()) {
                    intent.putExtra("windSpeedState", true);
                }
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        enteredCityName = findViewById(R.id.enteredCityName);
        checkBoxHumidity = findViewById(R.id.checkBoxHumidity);
        checkBoxAirPressure = findViewById(R.id.checkBoxAirPressure);
        checkBoxWindSpeed = findViewById(R.id.checkBoxWindSpeed);
        sendQueryButton = findViewById(R.id.checkWeatherButton);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getBaseContext(), "onStart", Toast.LENGTH_SHORT).show();
        Log.i(ACTIVITY_STATE_TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getBaseContext(), "onPause", Toast.LENGTH_SHORT).show();
        Log.i(ACTIVITY_STATE_TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getBaseContext(), "onStop", Toast.LENGTH_SHORT).show();
        Log.i(ACTIVITY_STATE_TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(), "onDestroy", Toast.LENGTH_SHORT).show();
        Log.i(ACTIVITY_STATE_TAG, "onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getBaseContext(), "onRestart", Toast.LENGTH_SHORT).show();
        Log.i(ACTIVITY_STATE_TAG, "onRestart");
    }
}
