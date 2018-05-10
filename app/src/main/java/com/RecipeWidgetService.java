package com;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Recipe;
import com.eliassilva.bakingapp.RecipeWidgetProvider;
import com.eliassilva.bakingapp.utilities.NetworkUtils;

import java.util.List;

/**
 * Created by Elias on 09/05/2018.
 */
public class RecipeWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

    class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        private List<Recipe> mRecipes;
        private Context mContext;
        private int mAppWidgetId;

        public RecipeRemoteViewsFactory(Context applicationContext, Intent intent) {
            mContext = applicationContext;
            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mRecipes.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            try {
                mRecipes = NetworkUtils.extractRecipesFromJson();
                Log.d("Try ", "entrou");
            } catch (RuntimeException e) {
                e.printStackTrace();
                Log.d("Catch ", "entrou");
            }

            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);
            rv.setTextViewText(R.id.widget_recipe_name, mRecipes.get(position).getRecipeName());

            Bundle extras = new Bundle();
            extras.putInt(RecipeWidgetProvider.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            rv.setOnClickFillInIntent(R.id.widget_recipe_name, fillInIntent);

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
