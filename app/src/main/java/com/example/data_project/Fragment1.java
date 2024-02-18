package com.example.data_project;

import static android.app.Activity.RESULT_OK;

import static com.example.data_project.GenreList.genreList;
import static com.example.data_project.PlatformList.platformList;

import static kotlinx.coroutines.BuildersKt.withContext;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Fragment1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int REQUEST_CODE_LIST = 101;
    public static final int REQUEST_CODE_LIST2 = 102;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static Context mContext;
    static ArrayList<String> bookmark = new ArrayList<>();
    EditText platformEditText, genreEditText, searchEditText;
    TextView nameText, scoreText;
    Button dataButton, searchButton;
    RecyclerView recyclerView;
    static DataAdapter adapter = new DataAdapter();
    JSONArray name;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View l = inflater.inflate(R.layout.fragment_1, container, false);
        Button platformButton = l.findViewById(R.id.platformButton);
        platformButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PlatformList.class);
                startActivityForResult(intent, REQUEST_CODE_LIST);
            }
        });
        Button genreButton = l.findViewById(R.id.genreButton);
        genreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GenreList.class);
                startActivityForResult(intent, REQUEST_CODE_LIST2);
            }
        });
        platformEditText = l.findViewById(R.id.platformEditText);
        platformEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String platform = String.valueOf(v.getText());
                if (!platformList.contains(platform)) {
                    platformEditText.setText("");
                    printToast("플랫폼 리스트에 속한 것이 아닙니다.\n" +
                            "플랫폼 리스트 버튼을 눌러 확인해주세요.");
                }
                return false;
            }
        });
        genreEditText = l.findViewById(R.id.genreEditText);
        genreEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String genre = String.valueOf(v.getText());
                if (!genreList.contains(genre)) {
                    genreEditText.setText("");
                    printToast("장르 리스트에 속한 것이 아닙니다.\n" +
                            "장르 리스트 버튼을 눌러 확인해주세요.");
                }
                return false;
            }
        });
        searchEditText = l.findViewById(R.id.searchEditText);
        searchButton = l.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dataRequest(); }
        });

        nameText = l.findViewById(R.id.nameText);
        scoreText = l.findViewById(R.id.scoreText);
        dataButton = l.findViewById(R.id.dataButton);
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataRequest();
            }
        });

        recyclerView = l.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        return l;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LIST) { // 플랫폼이 REQUEST_CODE_LIST
            if (resultCode == RESULT_OK) {
                String val = data.getStringExtra("val");
                platformEditText.setText(val);
            }
        } else if (requestCode == REQUEST_CODE_LIST2) { // 장르가 REQUEST_CODE_LIST2
            if (resultCode == RESULT_OK) {
                String val = data.getStringExtra("val");
                genreEditText.setText(val);
            }
        }
    }
    public static void printToast(String s) { Toast.makeText(mContext.getApplicationContext(), s, Toast.LENGTH_SHORT).show(); }
    ProgressDialog dialog;
    public void showSpinnerDialog() {
        dialog = new ProgressDialog(mContext);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false); // dialog 외부 클릭으로 종료 X
        dialog.setMessage("데이터 요청 중입니다...");
        dialog.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, 1500); // 스피너를 띄운 후 1.5초 뒤에 꺼지게 함.
    }
    public void dataRequest() {
        showSpinnerDialog();
        String s = searchEditText.getText().toString().toUpperCase();
        boolean bl = s.equals(""); // 검색 옆 텍스트가 입력되어있는지?
        // assets 폴더의 파일을 가져오기 위해
        // 창고관리자인 assetManager를 불러오기.
        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream ip = assetManager.open("videoGame.json");
            InputStreamReader ipr = new InputStreamReader(ip);
            BufferedReader rd = new BufferedReader(ipr);

            StringBuffer bf = new StringBuffer();
            String line = rd.readLine();
            while (line != null) {
                bf.append(line+"\n");
                line = rd.readLine();
            }

            String data = bf.toString();
            JSONArray jsonArray = new JSONArray(data);
            String str = "";
            String ipplat = String.valueOf(platformEditText.getText());
            String ipgenre = String.valueOf(genreEditText.getText());

            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject JSONobj = jsonArray.getJSONObject(i);
                String plat = JSONobj.getString("Platform"); // 입력한 platform 값 가져오기.
                String gen = JSONobj.getString("Genre"); // 입력한 genre 값 가져오기.
                if (ipplat.equals(plat)) {
                    if (ipgenre.equals(gen)) {
                        adapter.clear();
                        adapter.notifyDataSetChanged();
                        name = JSONobj.getJSONArray("Name");
                        double user_score = JSONobj.getDouble("User_Score");
                        str += plat + "-" + gen;
                        for (int j=0; j<name.length(); j++) {
                            String val = (String) name.get(j);
                            val = val.toUpperCase();
                            if (bl) { adapter.addItem(val); } // 검색 옆 editText에 아무 것도 입력 되어 있지 않을때.
                            else if (!bl && val.contains(s)) { adapter.addItem(val); }
                        }
                        nameText.setText(str);
                        scoreText.setText("평점: " + String.format("%.2f", user_score));
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
            if (str.equals("")) { printToast("해당 입력하신 플랫폼과 장르에 맞는 데이터가 없습니다."); }
        } catch (IOException e) { e.printStackTrace(); } catch (JSONException e) { e.printStackTrace(); }
    }
}