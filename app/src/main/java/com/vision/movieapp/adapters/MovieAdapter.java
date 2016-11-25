package com.vision.movieapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.vision.movieapp.R;
import com.vision.movieapp.models.Movie;
import com.vision.movieapp.utils.Constants;

import java.util.List;

/**
 * Created by Vision on 05/10/2016.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context = getContext();


    public MovieAdapter(Context context, List<Movie> movies) {
        super(context, 0, movies);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridItemView = convertView;
        Movie movie = getItem(position);


        if (gridItemView == null) {
            gridItemView = LayoutInflater.from(context).inflate(R.layout.movie_poster_item, parent, false);
        }

        ImageView posterView = (ImageView) gridItemView.findViewById(R.id.imageView_poster);
        String stringURL = Constants.BASE_URL_POSTER + Constants.POSTER_SMALL_SIZE + movie.getmImageResource();
        Picasso.with(context).load(stringURL).into(posterView);
        Log.d("Movie", stringURL);


        return gridItemView;
    }
}
