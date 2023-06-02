package com.example.rxjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import io.reactivex.rxjava3.core.Observable;

public class DayTwoActivity extends AppCompatActivity {
    Button countBtn;
    Button outBtn;
    Button debounceBtn;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_two);
        countBtn = findViewById(R.id.countBtn);
        outBtn = findViewById(R.id.checkOutBtn);
        debounceBtn = findViewById(R.id.debounceBtn);
        searchBtn = findViewById(R.id.searchBtn);

        countBtn.setOnClickListener(v -> getCountOfStrings());
        outBtn.setOnClickListener(v -> checkOutput());
        debounceBtn.setOnClickListener(v -> useDebounce());
        searchBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        });
    }


    private void getCountOfStrings() {
        List<String> names = List.of("Patric Ross", "Kelly Wood", "James Moore", "Janice Coleman", "Mary Carter");
        long desiredCount = names.stream().filter(name -> (name.length() < 12) && name.startsWith("J")).count();
        System.out.println("Number Of Names Starts With J: " + Math.toIntExact(desiredCount));
    }

    private void checkOutput() {
        Stream<String> stream = Stream.of("one", "two", "three", "four");
        boolean match = stream.anyMatch(s -> s.contains("four"));
        System.out.println(match);
    }

    private void useDebounce() {
        Observable<String> observable = Observable.create(emitter -> {
            emitter.onNext("First Emit");
            Thread.sleep(100);
            emitter.onNext("Second Emit");
            Thread.sleep(100);
            emitter.onNext("Third");
            Thread.sleep(100);
            emitter.onNext("Fourth Emit");
            Thread.sleep(100);
            emitter.onNext("Fifth Emit");
        });

        observable
                .debounce(200, TimeUnit.MILLISECONDS)
                .subscribe(System.out::println);


    }

}