package com.example.data_project;

import static com.example.data_project.MarkAdapter.marks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static Context mContext2;
    RecyclerView recyclerView2;
    static MarkAdapter adapter2 = new MarkAdapter();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext2 = context;
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
        View l = inflater.inflate(R.layout.fragment_2, container, false);
        recyclerView2 = l.findViewById(R.id.recyclerview2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView2.setLayoutManager(linearLayoutManager);
        recyclerView2.setAdapter(adapter2);
        // 아직 구현 X
//        SharedPreferences pref = mContext2.getSharedPreferences("mk", Context.MODE_PRIVATE);
//        String jo = pref.getString("mk", null);
//        if (jo != null) {
//            try {
//                JSONArray ja = new JSONArray(jo);
//                for (int i=0; i<ja.length(); i++) { marks.add(ja.optString(i)); }
//            } catch (JSONException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        ArrayList<String> tmp = marks;
        return l;
    }
}