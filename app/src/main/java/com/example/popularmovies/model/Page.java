package com.example.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Page {

    @SerializedName("page")
    int page;

    @SerializedName("total_results")
    int totalResults;

    @SerializedName("total_pages")
    int totalPages;

    @SerializedName("results")
    List<Movie> moviesList;

    public Page(List<Movie> moviesList, int page, int totalResults, int totalPages) {
        this.page = page;
        this.totalResults = totalResults;
        this.totalPages = totalPages;
        this.moviesList = moviesList;
    }

    public List<Movie> getMoviesList() {
        return moviesList;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
