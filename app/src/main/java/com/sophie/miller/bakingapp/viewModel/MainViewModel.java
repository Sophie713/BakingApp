package com.sophie.miller.bakingapp.viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sophie.miller.bakingapp.objects.RecipeObject;
import com.sophie.miller.bakingapp.utils.GeneralUtils;
import com.sophie.miller.bakingapp.utils.RetrofitAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<RecipeObject>> recipes;

    private Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://d17h27t6h515a5.cloudfront.net/")
            .build();
    private RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    //todo editable??
    private Call<List<RecipeObject>> call = retrofitAPI.getRecipes("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json");


    public MainViewModel(Application application) {
        super(application);
        recipes = initializeRecipes();
                GeneralUtils.log("instantialized vm");
    }

    public LiveData<List<RecipeObject>> getRecipes() {
        GeneralUtils.log("got recipes");
        return recipes;
    }

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

}
