package com.eliassilva.bakingapp.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.eliassilva.bakingapp.Ingredient;
import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.adapters.IngredientAdapter;

import java.util.List;

/**
 * Created by Elias on 02/05/2018.
 */
public class RecipeDetailsFragment extends Fragment{
    private List<Ingredient> mIngredients;

    public RecipeDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_ingredients, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.ingredients_lv);

        IngredientAdapter mAdapter = new IngredientAdapter(getContext(), mIngredients);
        listView.setAdapter(mAdapter);

        return rootView;
    }

    public void setIngredientsData(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }
}
