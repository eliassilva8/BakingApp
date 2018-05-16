package com.eliassilva.bakingapp.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Step;

/**
 * Created by Elias on 04/05/2018.
 */
public class StepDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_details);

        final Step stepData = getIntent().getParcelableExtra("step");
        assert stepData != null;

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            StepDetailsFragment stepFragment = new StepDetailsFragment();
            stepFragment.setStepData(stepData);
            fragmentManager.beginTransaction()
                    .add(R.id.step_details_container, stepFragment)
                    .commit();
        }
    }
}
