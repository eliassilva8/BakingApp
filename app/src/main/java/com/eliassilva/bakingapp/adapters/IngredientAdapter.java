package com.eliassilva.bakingapp.adapters;

import android.content.Context;
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
public class IngredientAdapter extends BaseAdapter {
    private Context mContext;
    private List<Ingredient> mIngredients;

    @BindView(R.id.ingredient_name_tv) TextView mNameTv;
    @BindView(R.id.ingredient_measure_tv) TextView mMeasureTv;
    @BindView(R.id.ingredient_quantity_tv) TextView mQuantityTv;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        this.mContext = context;
        this.mIngredients = ingredients;
    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.ingredients_item, parent, false);
        }
        ButterKnife.bind(this, convertView);

        mNameTv.setText(mIngredients.get(position).getName());
        mMeasureTv.setText(mIngredients.get(position).getMeasure());
        mQuantityTv.setText(String.valueOf(mIngredients.get(position).getQuantity()));

        return convertView;
    }
}
