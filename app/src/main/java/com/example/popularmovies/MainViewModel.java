package com.example.popularmovies;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.popularmovies.data.AppDataBase;
import com.example.popularmovies.model.Movie;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movies;

    public MainViewModel(Application application) {
        super(application);
        AppDataBase database = AppDataBase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        movies = database.movieDao().loadAllMovies();
    }

    LiveData<List<Movie>> getMovies() {
        return movies;
    }
}
