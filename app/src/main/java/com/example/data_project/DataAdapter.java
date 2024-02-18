package com.example.data_project;

import static com.example.data_project.Fragment1.printToast;
import static com.example.data_project.Fragment2.adapter2;
import static com.example.data_project.MarkAdapter.marks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    static ArrayList<String> items = new ArrayList<>();
    Button starButton;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.data_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void addItem(String item) { items.add(item); }
    public void clear() { items.clear(); }


    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameCard;
        ImageView starImage;
        Button starButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCard = itemView.findViewById(R.id.markCard);
            starImage = itemView.findViewById(R.id.markImage);
            starButton = itemView.findViewById(R.id.starButton);
            starButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!marks.contains(nameCard.getText().toString())) {
                        starImage.setImageResource(R.drawable.fullstar);
                        adapter2.addItem(nameCard.getText().toString());
                        printToast("즐겨찾기에 추가되었습니다.");
                    }
                    else {
                        starImage.setImageResource(R.drawable.emptystar);
                        adapter2.removeItem(nameCard.getText().toString());
                        printToast("즐겨찾기에서 해제되었습니다.");
                    }
                    adapter2.notifyDataSetChanged();

                }
            });
        }
        public void setItem(String item) {
            if (marks.contains(item)) { starImage.setImageResource(R.drawable.fullstar); } // 북마크 해놓은 아이템이라면 노란별.
            else { starImage.setImageResource(R.drawable.emptystar); } // 북마크가 아니라면 빈 별.
            nameCard.setText(item);
        }
    }
}
