package com.sophie.miller.bakingapp.utils;

import androidx.lifecycle.MutableLiveData;

import com.sophie.miller.bakingapp.objects.RecipeObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RetrofitAPI {

    @GET
    Call<List<RecipeObject>> getRecipes(@Url String url);
}
