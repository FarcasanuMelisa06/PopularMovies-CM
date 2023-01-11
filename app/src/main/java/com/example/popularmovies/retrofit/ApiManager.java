package com.example.popularmovies.retrofit;

import com.example.popularmovies.model.Page;
import com.example.popularmovies.utils.Constants;

import androidx.constraintlayout.widget.Constraints;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {

    private static ApiManager sApiManager;
    private static JsonPlaceHolderApi sApiInterface;

    private ApiManager() {
        Retrofit retrofitForced = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sApiInterface = retrofitForced.create(JsonPlaceHolderApi.class);
    }

    public static ApiManager getInstance() {
        if (sApiManager == null) {
            sApiManager = new ApiManager();
        }
        return sApiManager;
    }

    public void getPage(int number, String apiKey, String sortType, Callback<Page> callback) {
        Call<Page> response = sApiInterface.getPage(number, apiKey, sortType);
        response.enqueue(callback);
    }
}
