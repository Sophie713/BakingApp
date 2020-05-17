package com.sophie.miller.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.sophie.miller.bakingapp.MainActivity;
import com.sophie.miller.bakingapp.R;
import com.sophie.miller.bakingapp.databinding.WidgetActivitySetupBinding;
import com.sophie.miller.bakingapp.holders.HolderListItemSingleString;
import com.sophie.miller.bakingapp.objects.IngredientObject;
import com.sophie.miller.bakingapp.objects.RecipeObject;
import com.sophie.miller.bakingapp.prefs.Prefs;
import com.sophie.miller.bakingapp.utils.GeneralUtils;
import com.sophie.miller.bakingapp.viewModel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class WidgetSetupActivity extends AppCompatActivity {

    WidgetActivitySetupBinding binding;
    int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    ArrayList<String> recipes = new ArrayList<>();
    ArrayList<RecipeObject> recipeObj = new ArrayList<>();
    WidgetRecipesAdapter recipesAdapter = new WidgetRecipesAdapter();
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = WidgetActivitySetupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            appWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
        }
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            Intent finish = new Intent();
            finish.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            setResult(RESULT_CANCELED, finish);
            finish();
        }
        binding.widgetActivitySetupRV.setLayoutManager(new LinearLayoutManager(this));
        binding.widgetActivitySetupRV.hasFixedSize();
        binding.widgetActivitySetupRV.setAdapter(recipesAdapter);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<RecipeObject>>() {
            @Override
            public void onChanged(List<RecipeObject> recipeObjects) {
                recipes.clear();
                recipeObj.clear();
                recipeObj.addAll(recipeObjects);
                for (int i = 0; i < recipeObjects.size(); i++) {
                    recipes.add(recipeObjects.get(i).getRecipeName());
                    GeneralUtils.log(recipeObjects.get(i).getRecipeName());
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

    void submitChoice(View view, int position) {
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        //save ingredients
        ArrayList<IngredientObject> ingredients = new ArrayList<>(recipeObj.get(position).getIngredients());
        String ingredientsJson = new Gson().toJson(ingredients);
        GeneralUtils.log(ingredientsJson);
        new Prefs().saveIngredients(ingredientsJson, this, String.valueOf(appWidgetId));


        RemoteViews remoteViews = new RemoteViews(this.getPackageName(), R.layout.widget_ingredients_list);
        //main activity on click
        remoteViews.setOnClickPendingIntent(R.id.appwidget_text, mainActivityIntent(this));
        //set adapter
        remoteViews.setRemoteAdapter(R.id.appwidget_listview, adapterIntent(this, appWidgetId));
        remoteViews.setEmptyView(R.id.appwidget_listview, R.id.appwidget_text);

        Intent finish = new Intent();
        finish.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_OK, finish);
        finish();


    }

    private PendingIntent mainActivityIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 0, intent, 0);
    }

    private Intent adapterIntent(Context context, int appWidgetId){
        Intent intent = new Intent(context, WidgetIngredientsService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        return intent;
    }

    private class WidgetRecipesAdapter extends RecyclerView.Adapter<HolderListItemSingleString> {

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
                submitChoice(v, position);
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
}
