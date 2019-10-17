package com.example.geekbrainsweather;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText enteredCityName;
    private FloatingActionButton floatingActionButton;

    private MaterialButton sendQueryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setFloatingActionButtonBehaviour();
        setSendQueryButtonBehaviour();

    }

    private void setSendQueryButtonBehaviour() {
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
    }

    private void initViews() {
        floatingActionButton = findViewById(R.id.floatingActionButton2);
        enteredCityName = findViewById(R.id.enteredCityName);
        sendQueryButton = findViewById(R.id.checkWeatherButton);

    }

    private void setFloatingActionButtonBehaviour() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, getString(R.string.created_by), Snackbar.LENGTH_LONG)
                        .setAction("Action",null)
                        .show();
            }
        });
    }
}
