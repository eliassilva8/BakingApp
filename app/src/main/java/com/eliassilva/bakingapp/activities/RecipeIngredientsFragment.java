package com.eliassilva.bakingapp.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eliassilva.bakingapp.Ingredient;
import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.RecipeWidgetProvider;
import com.eliassilva.bakingapp.adapters.IngredientAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elias on 02/05/2018.
 */
public class RecipeIngredientsFragment extends Fragment{
    private List<Ingredient> mIngredients;
    @BindView(R.id.ingredients_rv) RecyclerView mIngredientsRecyclerView;

    public RecipeIngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mIngredientsRecyclerView.setLayoutManager(layoutManager);
        mIngredientsRecyclerView.setHasFixedSize(true);
        mIngredientsRecyclerView.setNestedScrollingEnabled(false);

        IngredientAdapter mAdapter = new IngredientAdapter(mIngredients);
        mIngredientsRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    public void setIngredientsData(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }
}
