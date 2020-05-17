package com.sophie.miller.bakingapp.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.sophie.miller.bakingapp.utils.Const;

public class Prefs {

    private static final String PREFS = "prefs";

    public void saveIngredients(String json, Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(Const.WIDGET_DATA, json).apply();
    }

    public String getIngredients(Context context){
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getString(Const.WIDGET_DATA, "");
    }
}
