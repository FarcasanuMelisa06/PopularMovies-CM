package com.example.popularmovies.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "movies")
public class Movie implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "title")
    @SerializedName("title")
    private String OriginalTitle;

    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    private String thumbnail;

    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    private float voteAverage;

    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    private String overview;

    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    private String date;

    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    private float popularity;

    @Ignore
    public Movie(String originalTitle, String thumbnail, float voteAverage, String overview, String date, float popularity) {
        OriginalTitle = originalTitle;
        this.thumbnail = thumbnail;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.date = date;
        this.popularity = popularity;
    }

    public Movie(int id, String originalTitle, String thumbnail, float voteAverage, String overview, String date, float popularity) {
        OriginalTitle = originalTitle;
        this.thumbnail = thumbnail;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.date = date;
        this.popularity = popularity;
        this.id = id;
    }

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginalTitle(String originalTitle) {
        OriginalTitle = originalTitle;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getOriginalTitle() {
        return OriginalTitle;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getDate() {
        return date;
    }
}
