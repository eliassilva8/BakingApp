package com.eliassilva.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Step;
import com.eliassilva.bakingapp.adapters.StepAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elias on 02/05/2018.
 */
public class RecipeStepsFragment extends Fragment implements StepAdapter.StepAdapterOnClickHandler {
    private List<Step> mSteps;
    @BindView(R.id.steps_rv)
    RecyclerView mStepRecylerView;
    boolean mIsTwoPane;

    public RecipeStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, rootView);
        mIsTwoPane = this.getArguments().getBoolean("is_two_pane");

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mStepRecylerView.setLayoutManager(layoutManager);
        mStepRecylerView.setHasFixedSize(true);
        mStepRecylerView.setNestedScrollingEnabled(false);

        final StepAdapter mAdapter = new StepAdapter(mSteps, this);
        mStepRecylerView.setAdapter(mAdapter);
        return rootView;
    }

    public void setStepsData(List<Step> steps) {
        mSteps = steps;
    }

    @Override
    public void onClick(Step step) {
        if (mIsTwoPane) {
            StepDetailsFragment stepsDetailsFragment = new StepDetailsFragment();
            stepsDetailsFragment.setStepData(step);
            getFragmentManager().beginTransaction().replace(R.id.recipe_steps_details_container, stepsDetailsFragment).commit();
        } else {
            Step stepToSend = new Step(step.getShortDescription(), step.getDescription(), step.getVideoUrl(), step.getImageUrl());
            Intent intent = new Intent(getActivity(), StepDetailsActivity.class);
            intent.putExtra("step", stepToSend);
            startActivity(intent);
        }
    }
}
