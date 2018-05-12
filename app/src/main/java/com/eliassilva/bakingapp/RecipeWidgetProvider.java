package com.eliassilva.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.eliassilva.bakingapp.activities.MainActivity;
import com.eliassilva.bakingapp.activities.RecipeDetailsActivity;

import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RecipeService.startActionToWidget(context);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static RemoteViews getIngredientsRemoteView(Context context, Recipe recipe) {
        Intent intent = new Intent(context, ListViewService.class);
        intent.putExtra("recipe", recipe);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setRemoteAdapter(R.id.widget_ingredient_list, intent);
        Intent appIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widget_ingredient_list, pendingIntent);
        views.setEmptyView(R.id.widget_ingredient_list, R.id.empty_view);
        return views;
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, Recipe recipe) {
        for (int id : appWidgetIds) {
            RemoteViews rv = getIngredientsRemoteView(context, recipe);
            appWidgetManager.updateAppWidget(id, rv);
        }

    }
}

