package com.sophie.miller.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.sophie.miller.bakingapp.MainActivity;
import com.sophie.miller.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetIngredientsListProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //update all widgets
        for (int appWidgetId : appWidgetIds) {

            RemoteViews view = new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list);
            //main activity on click
            view.setOnClickPendingIntent(R.id.appwidget_text, mainActivityIntent(context));
           //set adapter
            view.setRemoteAdapter(R.id.appwidget_listview, adapterIntent(context, appWidgetId));
            view.setEmptyView(R.id.appwidget_listview, R.id.appwidget_text);


            appWidgetManager.updateAppWidget(appWidgetId, view);
        }
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
}

