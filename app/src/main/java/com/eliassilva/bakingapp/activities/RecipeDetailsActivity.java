package com.eliassilva.bakingapp.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Recipe;

/**
 * Created by Elias on 02/05/2018.
 */
public class RecipeDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        final Recipe recipeData = getIntent().getParcelableExtra("recipe");
        assert recipeData != null;

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();
        ingredientsFragment.setIngredientsData(recipeData.getIngredients());
        fragmentManager.beginTransaction()
                .add(R.id.recipe_ingredients_container, ingredientsFragment)
                .commit();

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        stepsFragment.setStepsData(recipeData.getSteps());
        fragmentManager.beginTransaction()
                .add(R.id.recipe_steps_container, stepsFragment)
                .commit();

    }
}
