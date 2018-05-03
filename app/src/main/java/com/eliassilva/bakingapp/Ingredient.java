package com.eliassilva.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Elias on 02/05/2018.
 */
public class Ingredient implements Parcelable {
    private String mName;
    private String mMeasure;
    private int mQuantity;

    public Ingredient(String name, String measure, int quantity) {
        this.mName = name;
        this.mMeasure = measure;
        this.mQuantity = quantity;
    }

    protected Ingredient(Parcel in) {
        mName = in.readString();
        mMeasure = in.readString();
        mQuantity = in.readInt();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getName() {
        return mName;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public int getQuantity() {
        return mQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mMeasure);
        dest.writeInt(mQuantity);
    }
}
