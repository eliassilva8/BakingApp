package com.eliassilva.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elias on 24/04/2018.
 */
public class Recipe implements Parcelable {
    private String mRecipeName;
    private int mNumberOfSteps;
    private List<Ingredient> mIngredients;
    private List<Step> mSteps;

    public Recipe(String recipeName, int numberOfSteps, List<Ingredient> ingredients, List<Step> steps) {
        this.mRecipeName = recipeName;
        this.mNumberOfSteps = numberOfSteps;
        this.mIngredients = ingredients;
        this.mSteps = steps;
    }

    protected Recipe(Parcel in) {
        mRecipeName = in.readString();
        mNumberOfSteps = in.readInt();
        if (mIngredients == null) {
            mIngredients = new ArrayList<>();
        }
        in.readTypedList(mIngredients, Ingredient.CREATOR);
        if (mSteps == null) {
            mSteps = new ArrayList<>();
        }
        in.readTypedList(mSteps, Step.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mRecipeName);
        dest.writeInt(mNumberOfSteps);
        dest.writeTypedList(mIngredients);
        dest.writeTypedList(mSteps);
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public int getNumberOfSteps() {
        return mNumberOfSteps;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }
}
