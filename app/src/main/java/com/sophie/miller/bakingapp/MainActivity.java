package com.sophie.miller.bakingapp;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.sophie.miller.bakingapp.adapters.RecipesAdapter;
import com.sophie.miller.bakingapp.databinding.ActivityMainBinding;
import com.sophie.miller.bakingapp.objects.RecipeObject;
import com.sophie.miller.bakingapp.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<String> recipes = new ArrayList<>();

    RecipesAdapter recipesAdapter = new RecipesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.activityMainRecyclerView.setLayoutManager(new GridLayoutManager(this, getNumberOfColumns()));
        binding.activityMainRecyclerView.hasFixedSize();
        binding.activityMainRecyclerView.setAdapter(recipesAdapter);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<RecipeObject>>() {
            @Override
            public void onChanged(List<RecipeObject> recipeObjects) {
                recipes.clear();
                for (int i = 0; i < recipeObjects.size(); i++) {
                    recipes.add(recipeObjects.get(i).getRecipeName());
                }
                recipesAdapter.setRecipes(recipes);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recipesAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * set number of columns based on screen size
     *
     * @return number of columns
     */
    private int getNumberOfColumns() {
        // make a grid with a certain number of columns
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        return (width/600);

    }
}
