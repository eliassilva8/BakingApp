package com.eliassilva.bakingapp.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.eliassilva.bakingapp.Recipe;

import java.util.List;

/**
 * Created by Elias on 24/04/2018.
 */
public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {
    public RecipeLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<Recipe> loadInBackground() {
        return NetworkUtils.extractRecipesFromJson();
    }
}
