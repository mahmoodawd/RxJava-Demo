package com.example.rxjavademo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchResultViewHolder> {
    private final Context context;
    private List<String> itemList;
    private static final String TAG = "SearchAdapter";

    private final int MEAL_VIEW = 0;
    private final int INGREDIENT_VIEW = 1;
    private final int AREA_VIEW = 2;
    private final int CATEGORY_VIEW = 3;


    public SearchAdapter(Context context, List<String> values) {
        itemList = new ArrayList<>();
        this.context = context;
        this.itemList = values;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void setNameList(List<String> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.raw_item_layout, parent, false);
        CardView cardView = (CardView) v;
        SearchResultViewHolder viewHolder = new SearchResultViewHolder(cardView);
        Log.i(TAG, "onCreateViewHolder");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        String name = itemList.get(position);
        holder.studentNameTV.setText(name);
        Log.i(TAG, "onBindViewHolder");

    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        public TextView studentNameTV;
        public CardView nameLayout;
        public View layout;

        public SearchResultViewHolder(View v) {
            super(v);
            layout = v;
            studentNameTV = v.findViewById(R.id.student_name_text_view);
            nameLayout = v.findViewById(R.id.name_layout);
        }
    }
}


