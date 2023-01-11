package com.example.popularmovies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.databinding.ActivityMainBinding;
import com.example.popularmovies.databinding.AppAdapterGridViewItemBinding;
import com.example.popularmovies.model.Movie;
import com.example.popularmovies.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context mContext;
    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onItemClickListener(int viewIndex);
    }

    private List<Movie> mMovies = new ArrayList<Movie>() { //lista originala
    };

    MovieAdapter(Context context, ListItemClickListener listener) {
        mOnClickListener = listener;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.app_adapter_grid_view_item, parent, false);
        return new ViewHolder(view);
    }

    void setMovies(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    public List<Movie> getMovies() {
        return mMovies;
    }

    @Override
    public int getItemCount() {
        return mMovies != null ? mMovies.size() : 0;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Movie currentMovie = mMovies.get(position);
        if (currentMovie.getThumbnail() != null)
            Picasso.get().load(Constants.IMAGE_BASE_URL + currentMovie.getThumbnail()).into(holder.binding.thumbnailMovie);
        else
            Picasso.get().load(Constants.NO_PICTURE_AVAILABLE_ICON).into(holder.binding.thumbnailMovie);
        if (currentMovie.getVoteAverage() < 10.0)
            holder.binding.voteAverage.setText(currentMovie.getVoteAverage() + "");
        else holder.binding.voteAverage.setText("10");
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        AppAdapterGridViewItemBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = AppAdapterGridViewItemBinding.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int viewIndex = getAdapterPosition();
            mOnClickListener.onItemClickListener(viewIndex);
        }
    }
}
