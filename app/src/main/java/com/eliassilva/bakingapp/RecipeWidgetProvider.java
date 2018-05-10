package com.eliassilva.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.RecipeWidgetService;
import com.eliassilva.bakingapp.activities.MainActivity;
import com.eliassilva.bakingapp.utilities.NetworkUtils;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {
    public static final String EXTRA_ITEM = "com.eliassilva.bakingapp.EXTRA_ITEM";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; ++i) {
            Intent intent = new Intent(context, RecipeWidgetService.class);
            context.startService(intent);

            /*intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
            rv.setRemoteAdapter(R.id.widget_ingredient_list, intent);
            rv.setEmptyView(R.id.widget_ingredient_list, R.id.empty_view);

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);*/
        }
//        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

