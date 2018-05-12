package com.eliassilva.bakingapp.activities;

import android.app.ActionBar;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Recipe;
import com.eliassilva.bakingapp.Step;

/**
 * Created by Elias on 04/05/2018.
 */
public class StepDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);


        int display_mode = getResources().getConfiguration().orientation;
        if (display_mode == Configuration.ORIENTATION_LANDSCAPE) {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
            getSupportActionBar().hide();
        }

        final Step stepData = getIntent().getParcelableExtra("step");
        assert stepData != null;


        FragmentManager fragmentManager = getSupportFragmentManager();

        StepDetailsFragment stepFragment = new StepDetailsFragment();
        stepFragment.setStepData(stepData);
        fragmentManager.beginTransaction()
                .add(R.id.step_details_container, stepFragment)
                .commit();
    }
}
