package com.sophie.miller.bakingapp.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sophie.miller.bakingapp.R;

public class HolderIngredient extends RecyclerView.ViewHolder {

    public TextView measure;
    public TextView ingredient;

    public HolderIngredient(@NonNull View itemView) {
        super(itemView);
        measure = itemView.findViewById(R.id.item_ingredient_number_units);
        ingredient = itemView.findViewById(R.id.item_ingredient_ingerdient);
    }
}
