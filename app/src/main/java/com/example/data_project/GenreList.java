package com.example.data_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class GenreList extends AppCompatActivity {

    ListView listView2;
    static ArrayList<String> genreList = new ArrayList<>(Arrays.asList("Action", "Adventure", "Fighting", "Misc", "Platform",
            "Puzzle", "Racing", "Role-Playing", "Shooter", "Simulation", "Sports", "Strategy"));;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_list);


        listView2 = findViewById(R.id.listView2);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genreList);
        listView2.setAdapter(adapter2);
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("val", genreList.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}