package com.eliassilva.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.eliassilva.bakingapp.R;
import com.eliassilva.bakingapp.Recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elias on 24/04/2018.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> mRecipesList;
    private RecipeAdapterOnClickHandler mOnClickHandler;

    public RecipeAdapter(RecipeAdapterOnClickHandler onClickHandler) {
        this.mOnClickHandler = onClickHandler;
    }

    public interface RecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe currentRecipe = mRecipesList.get(position);
        TextView recipeName = holder.mRecipeNameView;
        TextView numberOfSteps = holder.mNumberOfStepsView;
        ImageView recipeImage = holder.mRecipeImage;
        recipeName.setText(currentRecipe.getRecipeName());
        String numberOfStepsString = String.valueOf(mRecipesList.get(position).getNumberOfSteps());
        numberOfSteps.setText(numberOfStepsString);
        if (currentRecipe.getImageUrl().isEmpty() || currentRecipe.getImageUrl() == null) {
            recipeImage.setVisibility(View.GONE);
        } else {
            Picasso.get().load(currentRecipe.getImageUrl()).into(recipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return mRecipesList == null ? 0 : mRecipesList.size();
    }

    public void setRecipeData(List<Recipe> recipes) {
        mRecipesList = recipes;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipe_name_tv)
        TextView mRecipeNameView;
        @BindView(R.id.number_of_steps_tv)
        TextView mNumberOfStepsView;
        @BindView(R.id.recipe_image_iv)
        ImageView mRecipeImage;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Recipe recipeClicked = mRecipesList.get(getAdapterPosition());
            mOnClickHandler.onClick(recipeClicked);
        }
    }
}
