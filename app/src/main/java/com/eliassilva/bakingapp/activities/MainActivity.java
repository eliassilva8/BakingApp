package com.eliassilva.bakingapp.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Recipe;
import com.eliassilva.bakingapp.RecipeLoader;
import com.eliassilva.bakingapp.adapters.RecipeAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeAdapterOnClickHandler, LoaderManager.LoaderCallbacks<List<Recipe>> {
    @BindView(R.id.recipes_list_rv)
    RecyclerView mRecipesListView;
    private static final int ID_RECIPE_LOADER = 100;
    RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LoaderManager loader = getSupportLoaderManager();
        loader.initLoader(ID_RECIPE_LOADER, null, this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecipesListView.setLayoutManager(layoutManager);
        mRecipesListView.setHasFixedSize(true);
        mAdapter = new RecipeAdapter(this);
        mRecipesListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(Recipe recipe) {
        Recipe recipeToSend = new Recipe(recipe.getRecipeName(), recipe.getNumberOfSteps(), recipe.getIngredients(), recipe.getSteps());
        Intent intent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
        intent.putExtra("recipe", recipeToSend);
        startActivity(intent);

    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RecipeLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        mAdapter.setRecipeData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {
        mAdapter.setRecipeData(null);
    }
}
