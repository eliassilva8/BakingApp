package com.eliassilva.bakingapp.activities;

import android.content.Context;
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

    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    public RecipeStepsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_recipe_steps, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mStepRecylerView.setLayoutManager(layoutManager);
        mStepRecylerView.setHasFixedSize(true);
        mStepRecylerView.setNestedScrollingEnabled(false);

        final StepAdapter mAdapter = new StepAdapter(mSteps, this);
        mStepRecylerView.setAdapter(mAdapter);
        mStepRecylerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onStepSelected(mStepRecylerView.getChildLayoutPosition(v));
            }
        });
        return rootView;
    }

    public void setStepsData(List<Step> steps) {
        mSteps = steps;
    }

    @Override
    public void onClick(Step step) {
        Step stepToSend = new Step(step.getShortDescription(), step.getDescription(), step.getVideoUrl());
        Intent intent = new Intent(getActivity(), StepDetailsActivity.class);
        intent.putExtra("step", stepToSend);
        startActivity(intent);
    }
}
