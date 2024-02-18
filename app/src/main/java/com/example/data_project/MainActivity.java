package com.example.data_project;

import static com.example.data_project.Fragment1.printToast;
import static com.example.data_project.MarkAdapter.marks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_HELP = 102;

    class MyPagerAdapter extends FragmentStateAdapter {
        ArrayList<Fragment> fragList = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new Fragment1();
                case 1:
                    return new Fragment2();
                default:
                    return null;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
    Toolbar toolbar;
    ViewPager2 viewPager2;

    @Override
    protected void onStop() {
        super.onStop();
        // 아직 구현 X
//        SharedPreferences pref = getSharedPreferences("mk", MODE_PRIVATE);
//        SharedPreferences.Editor editor = pref.edit();
//        JSONArray ja = new JSONArray();
//        for (int i=0; i<marks.size(); i++) { ja.put(marks.get(i)); }
//        editor.putString("marks", ja.toString());
//        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false); // 액션바 세팅.

        TabLayout tabs = findViewById(R.id.tabs);
        viewPager2 = findViewById(R.id.viewPager2);
        viewPager2.setOffscreenPageLimit(2);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabs, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == 0) { tab.setText("비디오 메뉴 검색"); }
                else if (position == 1) { tab.setText("즐겨찾기"); }
            }
        }).attach();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int curId = item.getItemId();
        if (curId == R.id.help) {
            Toast.makeText(this, "도움말 클릭", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HelpActivity.class);
            startActivityForResult(intent, REQUEST_CODE_HELP);
        }
        return super.onOptionsItemSelected(item);
    }

    long preTime = 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - preTime >= 2000) { // 2초 지나기 전에 한번 더 뒤로가기 시 종료됨.
            preTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else {
            super.onBackPressed();
        }
    }
}