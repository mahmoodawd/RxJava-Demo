package com.example.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button dayOneBtn;
    Button dayTwoBtn;
    Button dayThreeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayOneBtn = findViewById(R.id.dayOneBtn);
        dayTwoBtn = findViewById(R.id.dayTwoBtn);
        dayThreeBtn = findViewById(R.id.dayThreeBtn);

        dayOneBtn.setOnClickListener(v -> navigate(DayOneActivity.class));
        dayTwoBtn.setOnClickListener(v -> navigate(DayTwoActivity.class));
        dayThreeBtn.setOnClickListener(v -> navigate(DayThreeActivity.class));


    }

    private void navigate(Class<?> destination) {
        Intent intent = new Intent(this, destination);
        startActivity(intent);
    }


}
