package com.example.rxjavademo;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DayThreeActivity extends AppCompatActivity {
    private EditText namesET;
    private EditText agesET;
    private TextView outPutTV;
    private Button zippingBtn;
    private Button checkThredsBtn;
    private Button composeBtn;
    private Button distinctBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_three);
        namesET = findViewById(R.id.nameField);
        agesET = findViewById(R.id.ageField);
        outPutTV = findViewById(R.id.outputTV);
        zippingBtn = findViewById(R.id.zippingBtn);
        checkThredsBtn = findViewById(R.id.rxThreadsBtn);
        composeBtn = findViewById(R.id.composeBtn);
        distinctBtn = findViewById(R.id.distinctBtn);


        zippingBtn.setOnClickListener(v -> performZipping());
        checkThredsBtn.setOnClickListener(v -> exploreRXThreads());
        composeBtn.setOnClickListener(v -> applyCompose());
        distinctBtn.setOnClickListener(v -> applyDistinct());

    }


    private void performZipping() {
        Observable.zip(getNames(), getAges(),
                        (names, ages) -> new Pair(names, ages))
                .subscribeOn(Schedulers.newThread())
                .doOnNext(item -> System.out.println(
                        "Before " + item + " On : " + Thread.currentThread().getName()))
                .observeOn(AndroidSchedulers.mainThread()).doOnNext(item -> System.out.println(
                        "After " + item + " On : " + Thread.currentThread().getName()))
                .subscribe(pair -> {
                            List<String> names = (List<String>) pair.first;
                            List<String> ages = (List<String>) pair.second;


                            outPutTV.setText("");
                            for (int i = 0; i < ages.size(); i++) {
                                outPutTV.append(names.get(i)
                                        .concat(" ")
                                        .concat(ages.get(i))
                                        .concat("\n"));
                            }
                        },
                        error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show());
    }

    Observable<List<String>> getNames() {
        Observable<List<String>> out;
        List<String> names = Arrays.asList(namesET.getText().toString().split("\n"));
        out = Observable.fromArray(names);
        return out;
    }

    Observable<List<String>> getAges() {
        Observable<List<String>> out;
        List<String> names = Arrays.asList(agesET.getText().toString().split("\n"));
        out = Observable.fromArray(names);
        return out;
    }

    private void exploreRXThreads() {
        Observable observable = Observable.create(emitter -> {
            Log.i("testSchedulers", "Hello MAD");
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
            emitter.onNext(4);
            emitter.onNext(5);
        });

        observable.subscribeOn(Schedulers.io())
                .doOnNext(item -> System.out.println(
                        "Emitting " + item + " On : " + Thread.currentThread().getName()))
                .observeOn(Schedulers.newThread())
                .doOnNext(item -> System.out.println(
                        "After Emitting " + item + " On : " + Thread.currentThread().getName()))
                .observeOn(Schedulers.computation())
                .doOnNext(item -> System.out.println(
                        "After Computation Thread Emitting " + item + " On : " + Thread.currentThread().getName()))
                .subscribe(item -> System.out.println(
                        "Consuming " + item + " On : " + Thread.currentThread().getName()));
    }

    private void applyCompose() {
        Observable.just(1, 2, 3, 4, 5).compose(applyOperators()).subscribe(item -> {
            Log.i("Compose", String.valueOf(item));
        });

    }

    private ObservableTransformer<Integer, Integer> applyOperators() {
        return upstream -> upstream
                .filter(number -> number % 2 == 0)
                .map(number -> number * 2)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.trampoline());
    }

    private void applyDistinct() {
        Observable.just(1, 1, 2, 1, 5).distinctUntilChanged().subscribe(item -> {
            Log.i("Distinct", String.valueOf(item));
        });

    }
}