package com.example.popularmovies.data;

import android.content.Context;

import com.example.popularmovies.model.Movie;

public class DataBaseOperations {

    public static void saveMovieToDataBase(final Movie movie, Context context) {
        final AppDataBase mDb = AppDataBase.getInstance(context);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (movie.getId() > 0) {
                    System.out.println("Movie Updated");
                    System.out.println(movie.getId());
                    mDb.movieDao().updateMovie(movie);
                } else {
                    System.out.println("Movie Inserted");
                    mDb.movieDao().insertMovie(movie);
                }
            }
        });
    }

    public static void deleteMovie(final Movie movie, Context context) {
        final AppDataBase mDb = AppDataBase.getInstance(context);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.movieDao().deleteMovie(movie);
            }
        });
    }
}
