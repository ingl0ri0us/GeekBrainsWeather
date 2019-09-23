package com.example.geekbrainsweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initGreetingsText();
    }

    private void initGreetingsText() {
        TextView greetingsTextView = findViewById(R.id.greetingsTextView);
        String text = new GreetingsBuilder().getGreetings(getApplicationContext());
        greetingsTextView.setText(text);
    }
}
