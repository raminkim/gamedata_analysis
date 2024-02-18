package com.example.data_project;
import static com.example.data_project.Fragment1.adapter;
import static com.example.data_project.Fragment2.adapter2;
import static com.example.data_project.Fragment2.mContext2;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class MarkAdapter extends RecyclerView.Adapter<MarkAdapter.ViewHolder2> {
    static ArrayList<String> marks = new ArrayList<>();

    @NonNull
    @Override
    public MarkAdapter.ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.mark_item, parent, false);

        return new MarkAdapter.ViewHolder2(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkAdapter.ViewHolder2 holder, int position) {
        String mark = marks.get(position);
        holder.setItem(mark);
    }

    @Override
    public int getItemCount() {
        return marks.size();
    }
    public void addItem(String item) { marks.add(item); }
    public static void removeItem(String item) { marks.remove(item); }
    public void clear() { marks.clear(); }


    static class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView markCard;
        Button deleteButton, starButton;
        public ViewHolder2(@NonNull View itemView) {
            super(itemView);
            markCard = itemView.findViewById(R.id.markCard);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            starButton = itemView.findViewById(R.id.starButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(markCard.getText().toString());
                    adapter.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                }
            });
        }
        public void setItem(String item) {
            markCard.setText(item);
        }
    }
}
