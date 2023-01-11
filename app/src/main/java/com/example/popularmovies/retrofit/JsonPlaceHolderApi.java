package com.example.popularmovies.retrofit;

import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Page;
import com.example.popularmovies.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {

    @GET(Constants.POPULAR_MOVIES)
    Call<Page> getPage(@Query("page") int number, @Query("api_key") String apiKey, @Query("sort_by") String sortType);
}
