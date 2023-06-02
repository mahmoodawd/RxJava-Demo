package com.example.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;

public class DayOneActivity extends AppCompatActivity {
    Button firstButton;
    Button secondButton;
    Button thirdButton;
    Button fourthButton;
    TextView textView;
    StringBuilder stringBuilder;
    Observable<Integer> arrayObservable;
    Observable<Integer> iterableObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_one);
        firstButton = findViewById(R.id.firstBtn);
        secondButton = findViewById(R.id.secondBtn);
        thirdButton = findViewById(R.id.thirdBtn);
        fourthButton = findViewById(R.id.fourthBtn);
        textView = findViewById(R.id.tv);


        stringBuilder = new StringBuilder();
        Integer[] myArray = {1, 2, 3, 4, 5};
        Iterable<Integer> myIterable = Arrays.asList(myArray);
        arrayObservable = Observable.fromArray(myArray);
        iterableObservable = Observable.fromIterable(myIterable);

        firstButton.setOnClickListener(v -> observeOnArray());

        secondButton.setOnClickListener(v -> observeOnIterable());

        thirdButton.setOnClickListener(v -> createObservableUsingIntervalRange());

        fourthButton.setOnClickListener(v -> createObservableUsingNever());
    }

    //Day One

    private void observeOnArray() {
        arrayObservable.subscribe(
                item -> stringBuilder.append("arrayObservable emit: ").append(item.toString()).append("\n"),
                error -> stringBuilder.append("arrayObservable Error!\n")
                , () -> stringBuilder.append("arrayObservable completed\n"));
        textView.setText(stringBuilder);
    }

    private void observeOnIterable() {
        iterableObservable.subscribe(
                item -> stringBuilder.append("iterableObservable emit: ").append(item.toString()).append("\n"),
                error -> stringBuilder.append("iterableObservable Error!\n")
                , () -> stringBuilder.append("iterableObservable completed\n"));
        textView.setText(stringBuilder);
    }

    private void createObservableUsingIntervalRange() {
        stringBuilder.setLength(0);
        Observable<Long> longObservable = Observable
                .intervalRange(1, 5, 0, 1, TimeUnit.SECONDS);
        longObservable.subscribe(item -> {
                    stringBuilder.append(item.toString());
                    Log.i("interval", item.toString());
                    textView.setText(stringBuilder);
                },
                error -> stringBuilder.append(error.getMessage()).append("\n"));


    }

    private void createObservableUsingNever() {
        Observable<Integer> neverObservable = Observable.never();
        neverObservable.subscribe(
                //Infinite Operation
        );

    }
}