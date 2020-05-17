package com.sophie.miller.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sophie.miller.bakingapp.R;
import com.sophie.miller.bakingapp.objects.IngredientObject;
import com.sophie.miller.bakingapp.prefs.Prefs;
import com.sophie.miller.bakingapp.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

public class WidgetIngredientsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetIngredientsListFactory(getApplicationContext(), intent);
    }

    class WidgetIngredientsListFactory implements RemoteViewsFactory {

        private ArrayList<IngredientObject> ingredients = new ArrayList<>();
        Context context;

        public WidgetIngredientsListFactory(Context context, Intent intent) {
            this.context = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            String ingredientsJson = new Prefs().getIngredients(context);
            ingredients.clear();
            List<IngredientObject> list= new Gson().fromJson(ingredientsJson, new TypeToken<ArrayList<IngredientObject>>(){}.getType());
            ingredients.addAll(list);
            GeneralUtils.log(ingredients.get(0).getIngredientName());
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredients.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.item_ingredient);
            view.setTextViewText(R.id.item_ingredient_ingerdient, ingredients.get(position).getIngredientName());
            view.setTextViewText(R.id.item_ingredient_number_units,
                    ""+ingredients.get(position).getQuantity() + " "+ ingredients.get(position).getMeasureUnit());
            return view;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
