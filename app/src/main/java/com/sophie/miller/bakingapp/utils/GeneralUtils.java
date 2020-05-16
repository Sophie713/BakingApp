package com.sophie.miller.bakingapp.utils;

import android.util.Log;
import android.widget.Toast;

import com.sophie.miller.bakingapp.DetailActivity;

public final class GeneralUtils {

    public static void log(String message){
        Log.e("xyz", message);
    }

    public static void toast(DetailActivity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
