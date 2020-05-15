package com.sophie.miller.bakingapp.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class IngredientObject implements Parcelable {

    private double quantity;
    @SerializedName("measure")
    private String measureUnit;
    @SerializedName("ingredient")
    private String ingredientName;

    public IngredientObject(double quantity, String measureUnit, String ingredientName) {
        this.quantity = quantity;
        this.measureUnit = measureUnit;
        this.ingredientName = ingredientName;
    }

    protected IngredientObject(Parcel in) {
        quantity = in.readDouble();
        measureUnit = in.readString();
        ingredientName = in.readString();
    }

    public static final Creator<IngredientObject> CREATOR = new Creator<IngredientObject>() {
        @Override
        public IngredientObject createFromParcel(Parcel in) {
            return new IngredientObject(in);
        }

        @Override
        public IngredientObject[] newArray(int size) {
            return new IngredientObject[size];
        }
    };

    public double getQuantity() {
        return quantity;
    }

    public String getMeasureUnit() {
        return measureUnit;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measureUnit);
        dest.writeString(ingredientName);
    }
}
