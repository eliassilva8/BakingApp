package com.eliassilva.bakingapp.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
    @BindView(R.id.no_internet_tv)
    TextView mNoInternetTV;
    private static final int ID_RECIPE_LOADER = 100;
    private RecipeAdapter mAdapter;
    private NetworkReceiver mReceiver;
    private LoaderManager mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoader = getSupportLoaderManager();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new NetworkReceiver();
        this.registerReceiver(mReceiver, filter);

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            this.unregisterReceiver(mReceiver);
        }
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

    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager conn = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = conn.getActiveNetworkInfo();

            if (networkInfo != null && (networkInfo.getType() == ConnectivityManager.TYPE_WIFI || networkInfo.getType() == ConnectivityManager.TYPE_MOBILE)) {
                mLoader.initLoader(ID_RECIPE_LOADER, null, MainActivity.this);
                mRecipesListView.setVisibility(View.VISIBLE);
                mNoInternetTV.setVisibility(View.GONE);
            } else {
                mRecipesListView.setVisibility(View.GONE);
                mNoInternetTV.setVisibility(View.VISIBLE);
            }
        }
    }
}
