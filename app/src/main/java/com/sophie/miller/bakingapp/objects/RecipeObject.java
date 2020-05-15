package com.sophie.miller.bakingapp.objects;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeObject implements Parcelable {
    private int id;
    //json key value differs so I annotate it
    @SerializedName("name")
    private String RecipeName;
    private List<IngredientObject> ingredients;
    private List<StepsObject> steps;
    private int servings;

    public RecipeObject(int id, String RecipeName, List<IngredientObject> ingredients, List<StepsObject> steps, int servings) {
        this.id = id;
        this.RecipeName = RecipeName;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
    }

    protected RecipeObject(Parcel in) {
        id = in.readInt();
        RecipeName = in.readString();
        ingredients = in.createTypedArrayList(IngredientObject.CREATOR);
        steps = in.createTypedArrayList(StepsObject.CREATOR);
        servings = in.readInt();
    }

    public static final Creator<RecipeObject> CREATOR = new Creator<RecipeObject>() {
        @Override
        public RecipeObject createFromParcel(Parcel in) {
            return new RecipeObject(in);
        }

        @Override
        public RecipeObject[] newArray(int size) {
            return new RecipeObject[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public List<IngredientObject> getIngredients() {
        return ingredients;
    }

    public List<StepsObject> getSteps() {
        return steps;
    }

    public int getServings() {
        return servings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(RecipeName);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
    }
}
