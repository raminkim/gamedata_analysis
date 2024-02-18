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

public class PlatformList extends AppCompatActivity {
    ListView listView;
    static ArrayList<String> platformList = new ArrayList<>(Arrays.asList("DS", "PS", "PS2", "PS3", "PSP", "Wii", "X360"));;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_list);

        listView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, platformList);
        listView.setAdapter(adapter2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("val", platformList.get(position));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}