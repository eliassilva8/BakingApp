package com.eliassilva.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Recipe;
import com.eliassilva.bakingapp.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elias on 03/05/2018.
 */
public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {
    private List<Step> mSteps;
    private StepAdapterOnClickHandler mOnClickHandler;

    public StepAdapter(List<Step> steps, StepAdapterOnClickHandler onClickHandler) {
        this.mSteps = steps;
        this.mOnClickHandler = onClickHandler;
    }

    public interface StepAdapterOnClickHandler {
        void onClick(Step step);
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.steps_item, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        TextView stepShortDescription = holder.mShortDescription;
        stepShortDescription.setText(mSteps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return mSteps == null ? 0 : mSteps.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.step_short_description_tv)
        TextView mShortDescription;

        public StepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Step stepClicked = mSteps.get(getAdapterPosition());
            mOnClickHandler.onClick(stepClicked);
        }
    }
}
