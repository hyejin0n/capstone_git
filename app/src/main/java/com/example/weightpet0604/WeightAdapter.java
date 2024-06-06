package com.example.weightpet0604;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class WeightAdapter extends RecyclerView.Adapter<WeightAdapter.WeightViewHolder> {
    private List<Weight> weightList;

    public static class WeightViewHolder extends RecyclerView.ViewHolder {
        public TextView weightTextView;
        public TextView dateTimeTextView;

        public WeightViewHolder(View itemView) {
            super(itemView);
            weightTextView = itemView.findViewById(R.id.weightTextView);
            dateTimeTextView = itemView.findViewById(R.id.dateTimeTextView); // Ensure this ID matches your XML layout
        }
    }

    public WeightAdapter(List<Weight> weightList) {
        this.weightList = weightList;
    }

    @NonNull
    @Override
    public WeightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.weight_item, parent, false);
        return new WeightViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WeightViewHolder holder, int position) {
        Weight currentItem = weightList.get(position);
        holder.weightTextView.setText(String.valueOf(currentItem.getWeight()));
        holder.dateTimeTextView.setText(currentItem.getDateTime());
    }

    @Override
    public int getItemCount() {
        return weightList.size();
    }
}
