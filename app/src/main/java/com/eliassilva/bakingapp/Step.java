package com.eliassilva.bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Elias on 02/05/2018.
 */
public class Step implements Parcelable {
    private String mShortDescription;
    private String mDescription;
    private String mVideoUrl;

    public Step(String shortDescription, String description, String videoUrl) {
        this.mShortDescription = shortDescription;
        this.mDescription = description;
        this.mVideoUrl = videoUrl;
    }

    protected Step(Parcel in) {
        mShortDescription = in.readString();
        mDescription = in.readString();
        mVideoUrl = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getVideoUrl() {
        return mVideoUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mShortDescription);
        dest.writeString(mDescription);
        dest.writeString(mVideoUrl);
    }
}
