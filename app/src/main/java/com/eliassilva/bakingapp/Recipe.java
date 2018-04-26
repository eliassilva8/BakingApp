package com.eliassilva.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Elias on 24/04/2018.
 */
public class Recipe implements Parcelable {
    private String mRecipeName;
    private int mNumberOfSteps;

    public Recipe(String recipeName, int numberOfSteps) {
        this.mRecipeName = recipeName;
        this.mNumberOfSteps = numberOfSteps;
    }

    protected Recipe(Parcel in) {
        mRecipeName = in.readString();

        mNumberOfSteps = in.readInt();
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
    }

    public String getRecipeName() {
        return mRecipeName;
    }

    public int getNumberOfSteps() {
        return mNumberOfSteps;
    }
}
