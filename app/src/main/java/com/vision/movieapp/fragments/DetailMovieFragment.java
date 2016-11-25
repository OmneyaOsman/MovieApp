package com.vision.movieapp.fragments;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.vision.movieapp.BuildConfig;
import com.vision.movieapp.R;
import com.vision.movieapp.models.Movie;
import com.vision.movieapp.utils.Constants;

import org.parceler.Parcels;

import io.realm.Realm;

/**
 * Created by Vision on 09/10/2016.
 */

public class DetailMovieFragment extends Fragment {

    private ImageView posterView;
    private TextView overView;
    private TextView voteAverage;
    private TextView releaseDate;
    private TextView movieTitle;
    private FloatingActionButton reviewsIm;
    private FloatingActionButton trailersIm;
    private FloatingActionButton favoriteFab;
    private int mId;
    private Movie currentMovieObject;
    private Movie realmMovie;
    private static final String TAG = DetailMovieFragment.class.getSimpleName();
    private Realm realm;
    int favorite;
    Movie usedMovie;

    @Override
    public void onStart() {
        super.onStart();


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.detail_movie_fragment, container, false);
        initViews(root);

        Bundle arguments = getArguments();
        Intent intent = getActivity().getIntent();
        if(arguments != null){
            currentMovieObject = Parcels.unwrap(arguments.getParcelable("current Movie"));

        }else if(intent != null && intent.hasExtra("current Movie")){
            currentMovieObject = Parcels.unwrap(intent.getParcelableExtra("current Movie"));
        }

        if (currentMovieObject !=null) {

            String originalTitle = currentMovieObject.getmOriginalTitle();
            mId = currentMovieObject.getmId();
            movieTitle.setText(originalTitle);
            overView.setText(currentMovieObject.getmOverView());
            voteAverage.append(" " +currentMovieObject.getmVoteAverage());
            releaseDate.append(" " + currentMovieObject.getmReleaseDate());
            Picasso.with(getContext())
                    .load(Constants.BASE_URL_POSTER + "w500" + currentMovieObject.getmImageResource())
                    .into(posterView);

        }

        Toast.makeText(getActivity(), currentMovieObject.getmOriginalTitle(), Toast.LENGTH_SHORT).show();


        reviewsIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Constants.REVIEWS, mId);

            }
        });

        trailersIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(Constants.VIDEOS, mId);
            }
        });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        realm = Realm.getDefaultInstance();
        realmMovie = realm.where(Movie.class).equalTo("mId", mId).findFirst();

        if (realmMovie != null) {

            favorite = realmMovie.getFavorite();
            updateFab(favorite);
            usedMovie = realmMovie;
        } else {
            usedMovie = currentMovieObject;
        }

        favoriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favorite == 1)
                    favorite = 0;
                else if (favorite == 0)
                    favorite = 1;
                else {
                    favorite = 1;

                }

                updateRealm(usedMovie, favorite);
            }
        });

        realm.refresh();


    }


    @Override
    public void onStop() {
        super.onStop();
        realm.close();

    }

    // initialize views
    private void initViews(View root) {

        posterView = (ImageView) root.findViewById(R.id.poster_image_view_detail);
        movieTitle = (TextView) root.findViewById(R.id.movie_title);
        overView = (TextView) root.findViewById(R.id.overview_tv_detail);
        voteAverage = (TextView) root.findViewById(R.id.vote_average_tv_detail);
        releaseDate = (TextView) root.findViewById(R.id.date_tv_detail);
        reviewsIm = (FloatingActionButton) root.findViewById(R.id.reviews_im_detail);
        trailersIm = (FloatingActionButton) root.findViewById(R.id.trailers_image);
        favoriteFab = (FloatingActionButton) root.findViewById(R.id.floatingActionButton);


    }

//show dialog for trailers or Reviews

    public void showDialog(String path, int id) {

        String mURL = urlString(path, id);
        Bundle args = new Bundle();
        args.putString("mURL", mURL);
        args.putString("path", path);

        FragmentDialog fragmentDialog = new FragmentDialog();
        fragmentDialog.setArguments(args);
        fragmentDialog.show(getActivity().getFragmentManager(), "FragmentDialog");
    }

    // create reviews URL String

    public String urlString(String path, int id) {

        Uri baseURI = Uri.parse(Constants.MOVIE_DB_URL);
        Uri.Builder builder = baseURI.buildUpon();

        builder.appendPath(String.valueOf(id));
        builder.appendPath(path);

        builder.appendQueryParameter(Constants.API_PARAM, BuildConfig.MOVIE_DB_API_KEY);
        builder.build();
        Log.e(TAG, "urlString: " + builder.toString());
        return builder.toString();
    }


    //update Favorites
    private void updateRealm(Movie realmMovie, int favorite) {
        realm.beginTransaction();
        realmMovie.setFavorite(favorite);
        realm.copyToRealmOrUpdate(realmMovie);
        realm.commitTransaction();

        if (favorite == 0)
            Toast.makeText(getActivity(), "Movie Deleted From Favorites", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getActivity(), "Movie Added To Favorites", Toast.LENGTH_SHORT).show();
        updateFab(favorite);

    }


    //update FloatingActionBar ImageTint
    private void updateFab(int favorite) {
        if (favorite == 1)
            favoriteFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_orange_dark)));
        else
            favoriteFab.setImageTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.white)));
    }


}
