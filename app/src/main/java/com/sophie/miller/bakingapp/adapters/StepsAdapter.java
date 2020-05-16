package com.sophie.miller.bakingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sophie.miller.bakingapp.R;
import com.sophie.miller.bakingapp.holders.HolderListItemSingleString;
import com.sophie.miller.bakingapp.utils.BaseListener;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<HolderListItemSingleString> {
    private BaseListener lister;
    public StepsAdapter(BaseListener listener) {
        this.lister = listener;
    }

    private ArrayList<String> steps = new ArrayList<>();

    @NonNull
    @Override
    public HolderListItemSingleString onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_card, parent, false);
        return new HolderListItemSingleString(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderListItemSingleString holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            //set selected step in the viewmodel
            lister.setSelectedStep(position);
        });
        holder.recipeTitle.setText(steps.get(position));
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    /**
     * update data
     * @param newSteps new steps to show
     */
    public void setSteps(ArrayList<String> newSteps) {
        steps.clear();
        steps.addAll(newSteps);
    }
}