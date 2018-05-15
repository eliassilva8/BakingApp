package com.eliassilva.bakingapp.activities;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Recipe;
import com.eliassilva.bakingapp.RecipeService;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elias on 02/05/2018.
 */
public class RecipeDetailsActivity extends AppCompatActivity {
    ConstraintLayout mTwoPane;
    @BindView(R.id.send_to_widget_bt)
    Button mSendToWidget;
    private Recipe mRecipeData;
    boolean mIsTwoPane;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mTwoPane = findViewById(R.id.two_pane);
        mIsTwoPane = mTwoPane != null ? true : false;
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
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_two_pane", mIsTwoPane);
        stepsFragment.setArguments(bundle);
        stepsFragment.setStepsData(mRecipeData.getSteps());
        fragmentManager.beginTransaction()
                .add(R.id.recipe_steps_container, stepsFragment)
                .commit();
    }
}
