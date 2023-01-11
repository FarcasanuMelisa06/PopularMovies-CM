package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.popularmovies.data.DataBaseOperations;
import com.example.popularmovies.databinding.ActivityDetailsBinding;
import com.example.popularmovies.databinding.ActivityMainBinding;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    private ActivityDetailsBinding binding;
    private Menu menu;
    Movie selectedMovie;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        selectedMovie = (Movie) getIntent().getSerializableExtra("Movie");

        binding.durationMovie.setText("120min");
        assert selectedMovie != null;
        getSupportActionBar().setTitle(selectedMovie.getOriginalTitle());

        if (!selectedMovie.getOverview().equals(""))
            binding.overview.setText(selectedMovie.getOverview());
        else binding.overview.setText("This movie has no description!");

        if (!selectedMovie.getDate().equals("")) {
            String[] tempDate = selectedMovie.getDate().split("-");
            binding.releaseYear.setText(tempDate[0]);
        } else binding.releaseYear.setText("not specified");

        binding.voteAverage.setText(selectedMovie.getVoteAverage() + "" + "/10");

        if (selectedMovie.getThumbnail() != null)
            Picasso.get().load(Constants.IMAGE_BASE_URL + selectedMovie.getThumbnail()).into(binding.thumbnailMovie);
        else
            Picasso.get().load(Constants.NO_PICTURE_AVAILABLE_ICON).into(binding.thumbnailMovie);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        this.menu = menu;
        inflater.inflate(R.menu.activity_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.mark_as_favorite) {
            markAsNoFavorite();
            deleteMovieFromDataBase(selectedMovie, this);
        } else if (id == R.id.mark_as_no_favorite) {
            markAsFavorite();
            saveMovieToDataBase(selectedMovie, this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void markAsFavorite() {
        menu.getItem(1).setVisible(true);
        menu.getItem(0).setVisible(false);
    }

    private void markAsNoFavorite() {
        menu.getItem(1).setVisible(false);
        menu.getItem(0).setVisible(true);
    }

    private void saveMovieToDataBase(Movie movie, Context context) {
        DataBaseOperations.saveMovieToDataBase(movie, context);
    }

    private void deleteMovieFromDataBase(Movie movie, Context context) {
        DataBaseOperations.deleteMovie(movie, context);
    }
}
