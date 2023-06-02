package com.example.rxjavademo;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class SearchActivity extends AppCompatActivity {

    RecyclerView searchResultRecyclerView;
    SearchAdapter searchResultAdapter;
    SearchView searchView;
    Stream<String> namesStream;
    private List<String> namesList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fillNameList();
        initUI();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchName(query);
                hideSoftKeyBoard();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchName(newText);
                return false;
            }
        });
    }

    private void initUI() {
        setContentView(R.layout.activity_search);

        searchResultRecyclerView = findViewById(R.id.searchResultRecyclerView);
        searchView = findViewById(R.id.searchView);
        searchResultAdapter = new SearchAdapter(this, namesList);
        LinearLayoutManager searchResultLayoutManager = new LinearLayoutManager(this);
        searchResultLayoutManager.setOrientation(RecyclerView.VERTICAL);
        searchResultRecyclerView.setHasFixedSize(true);
        searchResultRecyclerView.setLayoutManager(searchResultLayoutManager);
        searchResultRecyclerView.setAdapter(searchResultAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        searchResultRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void fillNameList() {
        namesStream = Stream.of(
                "Abdelrahman esam saleh awaad",
                "Ahmed Abdelhamid Yousef",
                "ahmed mohamed abd elmonem",
                "Ahmed Mohamed Mohamed",
                "Ahmed nasr Mohamed hamed",
                "Aya Khaled Mohamed Ali",
                "Bassant Mohammed Hassan Mandouh",
                "bassem osama abdallah amin",
                "ehsan ahmed elsaeed mohamed",
                "Hassan Kamal mahmoud",
                "Mahmoud Ahmed Ismail",
                "Mahmoud Awad Abdulhamid",
                "mazen tarek ali",
                "Mena Ezzat Fawzey saleh",
                "Mohamed Adel Abdallah ahmed",
                "Mohamed Ali Salem Ali Salem",
                "Mohamed ElAmir Moutafa Danash",
                "Mohamed Gamal Mohamed Hamza",
                "Mohamed Mohamed Nader Mohamed",
                "mohamed taha daoud",
                "Nouran Elsayed Abdelhamed Farahat",
                "Omar Yahya Marei",
                "Reem Hesham Al sayed",
                "Shimaa Sayed Aboelmagd Muhammed",
                "Shrouk Mohamed Twfik");
        namesList = new ArrayList<>();
        namesStream.forEach(nameItem -> namesList.add(nameItem));

    }


    private void searchName(String name) {

        Stream<String> filteredList = namesList.stream().filter(s -> s.toLowerCase().contains(name.toLowerCase()));
        List<String> filteredNames = filteredList.collect(Collectors.toList());

        searchResultAdapter.setNameList(filteredNames);
    }


    private void hideSoftKeyBoard() {
        // Hide the keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view != null) {
            IBinder token = view.getWindowToken();
            imm.hideSoftInputFromWindow(token, 0);
        }
    }
}