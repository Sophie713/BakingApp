package com.sophie.miller.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sophie.miller.bakingapp.DetailActivity;
import com.sophie.miller.bakingapp.R;
import com.sophie.miller.bakingapp.holders.HolderListItemSingleString;
import com.sophie.miller.bakingapp.utils.Const;

import java.util.ArrayList;

public class RecipesAdapter extends RecyclerView.Adapter<HolderListItemSingleString> {

    private ArrayList<String> recipes = new ArrayList<>();

    @NonNull
    @Override
    public HolderListItemSingleString onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_card, parent, false);
        return new HolderListItemSingleString(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderListItemSingleString holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(Const.intentKeyChosenRecipe, position);
            context.startActivity(intent);
        });
        holder.recipeTitle.setText(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setRecipes(ArrayList<String> newRecipes) {
        recipes.clear();
        recipes.addAll(newRecipes);
    }
}
