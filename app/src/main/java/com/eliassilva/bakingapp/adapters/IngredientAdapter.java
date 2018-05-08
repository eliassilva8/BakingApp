package com.eliassilva.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eliassilva.bakingapp.Ingredient;
import com.eliassilva.bakingapp.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Elias on 02/05/2018.
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<Ingredient> mIngredients;

    public IngredientAdapter(List<Ingredient> ingredients) {
        this.mIngredients = ingredients;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredients_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        TextView ingredientName = holder.mNameTv;
        TextView ingredientMeasure = holder.mMeasureTv;
        TextView ingredientQuantity = holder.mQuantityTv;
        ingredientName.setText(mIngredients.get(position).getName());
        ingredientMeasure.setText(mIngredients.get(position).getMeasure());
        ingredientQuantity.setText(String.valueOf(mIngredients.get(position).getQuantity()));
    }

    @Override
    public int getItemCount() {
        return mIngredients == null ? 0 : mIngredients.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_name_tv) TextView mNameTv;
        @BindView(R.id.ingredient_measure_tv) TextView mMeasureTv;
        @BindView(R.id.ingredient_quantity_tv) TextView mQuantityTv;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
