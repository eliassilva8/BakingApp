package com.eliassilva.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

/**
 * Created by Elias on 09/05/2018.
 */
public class ListViewService extends RemoteViewsService {
    private static Recipe mRecipe;
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        mRecipe = intent.getParcelableExtra("recipe");
        return new RecipeRemoteViewsFactory(this.getApplicationContext(), mRecipe);
    }
}

class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Recipe mRecipe;
    private List<Ingredient> mIngredients;
    private Context mContext;

    public RecipeRemoteViewsFactory(Context applicationContext, Recipe recipe) {
        mContext = applicationContext;
        mRecipe = recipe;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mIngredients = mRecipe.getIngredients();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredients != null ? mIngredients.size() : 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients == null || mIngredients.size() == 0) {
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);
        rv.setTextViewText(R.id.widget_recipe_name, mRecipe.getRecipeName());
        rv.setTextViewText(R.id.widget_ingredient_name, mIngredients.get(position).getName());

        Bundle extras = new Bundle();
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.widget_ingredient_name, fillInIntent);

        return rv;
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
