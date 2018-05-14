package com.eliassilva.bakingapp.activities;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.eliassilva.bakingapp.ListViewService;
import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Recipe;
import com.eliassilva.bakingapp.RecipeService;
import com.eliassilva.bakingapp.RecipeWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elias on 02/05/2018.
 */
public class RecipeDetailsActivity extends AppCompatActivity implements RecipeStepsFragment.OnStepClickListener {
    /*@BindView(R.id.two_pane)
    ConstraintLayout mTwoPane;*/
    @BindView(R.id.send_to_widget_bt)
    Button mSendToWidget;
    private Recipe mRecipeData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        ButterKnife.bind(this);
        mRecipeData = getIntent().getParcelableExtra("recipe");
        assert mRecipeData != null;

        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeIngredientsFragment ingredientsFragment = new RecipeIngredientsFragment();
        ingredientsFragment.setIngredientsData(mRecipeData.getIngredients());
        fragmentManager.beginTransaction()
                .add(R.id.recipe_ingredients_container, ingredientsFragment)
                .commit();

        mSendToWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeService.setRecipeOnWidget(RecipeDetailsActivity.this, mRecipeData);
            }
        });

        RecipeStepsFragment stepsFragment = new RecipeStepsFragment();
        stepsFragment.setStepsData(mRecipeData.getSteps());
        fragmentManager.beginTransaction()
                .add(R.id.recipe_steps_container, stepsFragment)
                .commit();
    }

    @Override
    public void onStepSelected(int position) {
        /*if (mTwoPane != null) {
            StepDetailsFragment stepsDetailsFragment = new StepDetailsFragment();
            stepsDetailsFragment.setStepData(mRecipeData.getSteps().get(position));
            getSupportFragmentManager().beginTransaction().add(R.id.step_details_container, stepsDetailsFragment).commit();
        }*/
    }
}
