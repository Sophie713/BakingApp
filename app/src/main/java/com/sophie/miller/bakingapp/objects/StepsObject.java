package com.sophie.miller.bakingapp.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class StepsObject implements Parcelable {
    @SerializedName("id")
    private int stepNumber;
    @SerializedName("shortDescription")
    private String stepTitle;
    @SerializedName("description")
    private String stepDescription;
    private String videoURL;

    public StepsObject(int stepNumber, String stepTitle, String stepDescription, String videoURL) {
        this.stepNumber = stepNumber;
        this.stepTitle = stepTitle;
        this.stepDescription = stepDescription;
        this.videoURL = videoURL;
    }

    protected StepsObject(Parcel in) {
        stepNumber = in.readInt();
        stepTitle = in.readString();
        stepDescription = in.readString();
        videoURL = in.readString();
    }

    public static final Creator<StepsObject> CREATOR = new Creator<StepsObject>() {
        @Override
        public StepsObject createFromParcel(Parcel in) {
            return new StepsObject(in);
        }

        @Override
        public StepsObject[] newArray(int size) {
            return new StepsObject[size];
        }
    };

    public int getStepNumber() {
        return stepNumber;
    }

    public String getStepTitle() {
        return stepTitle;
    }

    public String getStepDescription() {
        return stepDescription;
    }

    public String getVideoURL() {
        return videoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepNumber);
        dest.writeString(stepTitle);
        dest.writeString(stepDescription);
        dest.writeString(videoURL);
    }
}
