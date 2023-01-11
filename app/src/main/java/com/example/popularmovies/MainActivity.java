package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.popularmovies.databinding.ActivityMainBinding;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.model.Page;
import com.example.popularmovies.retrofit.ApiManager;
import com.example.popularmovies.utils.Constants;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    private ActivityMainBinding binding;

    MovieAdapter mAdapter;
    static MenuItem indicatePage;
    static int currentPage = 1;
    static int mSortType = 0; // 0 pentru popularity (default) si 1 pt vote_average
    static Page mPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.myRecyclerView.setEmptyView(binding.emptyView);
        mAdapter = new MovieAdapter(MainActivity.this, this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        binding.myRecyclerView.setHasFixedSize(true);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // use a linear layout manager
            binding.myRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 3));
        } else binding.myRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));

        // specify an adapter (see also next example
        binding.myRecyclerView.setAdapter(mAdapter);

        getMoviesFromServer(currentPage, Constants.API_KEY, Constants.SORT_BY_POPULARITY);
    }

    @Override
    public void onItemClickListener(int viewIndex) {
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        intent.putExtra("Movie", mAdapter.getMovies().get(viewIndex));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        indicatePage = menu.findItem(R.id.page);
        indicatePage.setTitle(currentPage + "");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.next_page && currentPage < 1000) {
            currentPage++;
            changeMoviesPage();
        } else if (id == R.id.prev_page && currentPage > 1) {
            currentPage--;
            changeMoviesPage();
        } else if (id == R.id.high_rated_movies) {
            mSortType = 1;
            changeMoviesPage();
        } else if (id == R.id.high_popularity) {
            mSortType = 0;
            changeMoviesPage();
        } else if (id == R.id.favorite_movies) {
            setupViewModel();
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadingStateUi() {
        binding.loadingSpinner.setVisibility(View.VISIBLE);
    }

    private void successStateUi() {
        binding.loadingSpinner.setVisibility(View.INVISIBLE);
    }

    public void getMoviesFromServer(int page, String apiKey, String sortType) {
        loadingStateUi();
        ApiManager.getInstance().getPage(page, apiKey, sortType, new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (mPage == null) {
                            mPage = response.body();
                        }
                        mAdapter.setMovies(mPage.getMoviesList());
                        successStateUi();
                    }
                } else {
                    System.out.println("Code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void changeMoviesPage() {
        indicatePage.setTitle(currentPage + "");
        mPage = null;
        if (mSortType == 0)
            getMoviesFromServer(currentPage, Constants.API_KEY, Constants.SORT_BY_POPULARITY);
        else getMoviesFromServer(currentPage, Constants.API_KEY, Constants.SORT_BY_VOTE);
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                System.out.println(movies.size());
                mAdapter.setMovies(null);
                mAdapter.setMovies(movies);
                binding.loadingSpinner.setVisibility(View.INVISIBLE);
            }
        });
    }
}
