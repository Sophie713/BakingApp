package com.sophie.miller.bakingapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sophie.miller.bakingapp.R;
import com.sophie.miller.bakingapp.holders.HolderIngredient;
import com.sophie.miller.bakingapp.objects.IngredientObject;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<HolderIngredient> {

    private ArrayList<IngredientObject> ingredients = new ArrayList<>();

    @NonNull
    @Override
    public HolderIngredient onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new HolderIngredient(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderIngredient holder, int position) {
        holder.measure.setText(String.format("%s %s", ingredients.get(position).getQuantity(), ingredients.get(position).getMeasureUnit()));
        holder.ingredient.setText(ingredients.get(position).getIngredientName());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void setIngredients(ArrayList<IngredientObject> newIngredients) {
        ingredients.clear();
        ingredients.addAll(newIngredients);
    }
}
