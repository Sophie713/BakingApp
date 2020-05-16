package com.sophie.miller.bakingapp.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sophie.miller.bakingapp.R;

public class HolderListItemSingleString extends RecyclerView.ViewHolder {

    public TextView recipeTitle;
    CardView cardView;

    public HolderListItemSingleString(@NonNull View itemView) {
        super(itemView);
        recipeTitle = itemView.findViewById(R.id.item_recipe_card_title_TV);
        cardView = itemView.findViewById(R.id.item_recipe_card_CV);
    }
}
