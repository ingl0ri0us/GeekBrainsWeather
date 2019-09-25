package com.example.geekbrainsweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView counterTextView;
    private Button incrementButton;

    private String counterDataKey = "counter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnBtnClickBehaviour();
        Toast.makeText(getBaseContext(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        counterTextView = findViewById(R.id.counterTextView);
        incrementButton = findViewById(R.id.incrementButton);
    }

    private void setOnBtnClickBehaviour() {
        incrementButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                increaseCounter();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(counterDataKey,counterTextView.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String text = savedInstanceState.getString(counterDataKey,"");
        counterTextView.setText(text);
    }

    private void increaseCounter() {
        String testViewText = counterTextView.getText().toString();
        int counterValue = Integer.parseInt(testViewText);
        String newCounterValue = String.valueOf(++counterValue);

        counterTextView.setText(newCounterValue);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getBaseContext(), "onStart", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(getBaseContext(), "onPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(getBaseContext(), "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Toast.makeText(getBaseContext(), "onDestroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(getBaseContext(), "onRestart", Toast.LENGTH_SHORT).show();
    }
}
