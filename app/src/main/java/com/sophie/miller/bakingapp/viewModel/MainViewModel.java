package com.sophie.miller.bakingapp.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sophie.miller.bakingapp.objects.IngredientObject;
import com.sophie.miller.bakingapp.objects.RecipeObject;
import com.sophie.miller.bakingapp.objects.StepsObject;
import com.sophie.miller.bakingapp.utils.GeneralUtils;
import com.sophie.miller.bakingapp.utils.RetrofitAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel {
    //retrofit
    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
            .build();
    private RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
    private Call<List<RecipeObject>> call = retrofitAPI.getRecipes("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");

    //list of recipes
    private LiveData<List<RecipeObject>> recipes;
    //chosen recipe
    private RecipeObject chosenRecipe = new RecipeObject(0, "Recipe", new ArrayList< IngredientObject>(), new ArrayList<StepsObject>(), 0);
    //chosen recipe position
    private int chosenRecipePosition = 0;
    //chosen step
    private MutableLiveData<Integer> chosenStep = new MutableLiveData<>(0);

    //constructor
    public MainViewModel(Application application) {
        super(application);
        recipes = initializeRecipes();
                GeneralUtils.log("instantialized vm");
    }

    /**
     * recipes getter
     * @return LiveData<List<RecipeObject>>
     */
    public LiveData<List<RecipeObject>> getRecipes() {
        return recipes;
    }

    /**
     * function that returns live data array list of all recipes from the server
     * @return LiveData<List<RecipeObject>>
     */
    private LiveData<List<RecipeObject>> initializeRecipes() {
        final MutableLiveData<List<RecipeObject>> data = new MutableLiveData<>();
        call.enqueue(new Callback<List<RecipeObject>>() {
            @Override
            public void onResponse(Call<List<RecipeObject>> call, Response<List<RecipeObject>> response) {
                data.setValue(response.body());
                GeneralUtils.log("success");
            }

            @Override
            public void onFailure(Call<List<RecipeObject>> call, Throwable t) {
                GeneralUtils.log("failure");
            }
        });
        return data;

    }

    /**
     * @return position of the chosen recipe
     */
    public int getChosenRecipePosition() {
        return chosenRecipePosition;
    }

    /**
     * set chosen recipe position
     * @param chosenRecipePosition
     */
    public void setChosenRecipePosition(int chosenRecipePosition) {
        this.chosenRecipePosition = chosenRecipePosition;
    }

    /**
     * get current step
     * @return
     */
    public LiveData<Integer> getChosenStep() {
        return chosenStep;
    }

    /**
     * set chosen step
     * @param chosenStep
     */
    public void setChosenStep(int chosenStep) {
        this.chosenStep.setValue(chosenStep);
    }

    /**
     * get chosen recipe
     * @return
     */
    public RecipeObject getChosenRecipe() {
        return chosenRecipe;
    }

    /**
     * set chosen recipe
     * @param chosenRecipe
     */
    public void setChosenRecipe(RecipeObject chosenRecipe) {
        this.chosenRecipe = chosenRecipe;
    }
}
