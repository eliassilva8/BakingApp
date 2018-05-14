package com.eliassilva.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.eliassilva.bakingapp.utilities.NetworkUtils;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

/**
 * Created by Elias on 10/05/2018.
 */
public class RecipeService extends IntentService {
    private static Recipe mRecipe;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public RecipeService() {
        super("recipeService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredient_list);
        RecipeWidgetProvider.updateRecipeWidget(this, appWidgetManager, appWidgetIds, mRecipe);
    }

    public static void startActionToWidget(Context context) {
        Intent intent = new Intent(context, RecipeService.class);
        context.startService(intent);
    }

    public static void setRecipeOnWidget(Context context, Recipe recipe) {
        startActionToWidget(context);
        mRecipe = recipe;
    }
}
