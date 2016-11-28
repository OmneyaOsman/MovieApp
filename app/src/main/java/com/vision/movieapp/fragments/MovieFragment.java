package com.vision.movieapp.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vision.movieapp.BuildConfig;
import com.vision.movieapp.MainActivity;
import com.vision.movieapp.MovieClickListener;
import com.vision.movieapp.R;
import com.vision.movieapp.adapters.MovieAdapter;
import com.vision.movieapp.models.Movie;
import com.vision.movieapp.utils.Constants;
import com.vision.movieapp.utils.ParseJSON;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;

import static android.view.View.GONE;

/**
 * Created by Vision on 05/10/2016.
 */

public class MovieFragment extends Fragment  {

    @BindView(R.id.poster_fragment_grid_view)
    GridView gridView ;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.empty_view)
    TextView emptyText;
    private MovieAdapter movieAdapter;
    Realm realm ;
    private MovieClickListener movieListener ;
    ArrayList<Movie> movies;




    public void setMovieListener( MovieClickListener movieClickListener){
        this.movieListener = movieClickListener ;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movie_fragment, container, false);
        setMovieListener((MainActivity)getActivity());

        ButterKnife.bind(this ,rootView);

        progressBar.setVisibility(View.VISIBLE);
        gridView.setEmptyView(emptyText);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // get All Favorite Movies :D
        realm = Realm.getDefaultInstance();

        if (getChosenSort().equals(getString(R.string.pref_sort_by_favorites_value))) {
            if (realm == null || realm.where(Movie.class).equalTo("favorite", 1).findAll().size() == 0) {
                emptyText.setText(R.string.empty_favorites);

            } else {
                // get All favorite Movies from Realm
                RealmResults<Movie> results = realm.where(Movie.class).equalTo("favorite", 1).findAll();

                movieAdapter = new MovieAdapter(getActivity(), results);
                movieAdapter.setNotifyOnChange(true);
                gridView.setAdapter(movieAdapter);

            }
            progressBar.setVisibility(GONE);


        } else {
            sendRequest();
        }

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Movie currentMovie = movieAdapter.getItem(position);
                movieListener.onMovieClickListener(currentMovie);
                Toast.makeText(getActivity(), currentMovie.getmOriginalTitle(), Toast.LENGTH_SHORT).show();

            }
        });





    }

    // get loaction from sharedPreferences
    private String getChosenSort() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort = sharedPreferences.getString(
                getString(R.string.pref_sort_by_key),
                getString(R.string.pref_sort_by_default_value));

        return sort;
    }

    // check if Network available
    private boolean isNetworkConnected() {
        boolean isConnected = false;

        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            isConnected = networkInfo.isConnected();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    /*
    * build url of movies list @return url String*/

    private String buildUrl(){

        String sort = getChosenSort();

        Uri baseURI = Uri.parse(Constants.MOVIE_DB_URL);
        Uri.Builder builder = baseURI.buildUpon();

        builder.appendPath(sort);

        builder.appendQueryParameter(Constants.API_PARAM, BuildConfig.MOVIE_DB_API_KEY);
        builder.build();
        return builder.toString() ;

    }


    private void sendRequest(){

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                buildUrl(),
                null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(isNetworkConnected()){
                    showJson(response);




                }else {
                    emptyText.setText(R.string.no_internet);
                    progressBar.setVisibility(GONE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Movie>() {
            @Override
            public void onRequestFinished(Request<Movie> request) {
                progressBar.setVisibility(GONE);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    //initialize and set MovieAdapter
    private void showJson(JSONObject response){
        movies = ParseJSON.extractMovies(response);
        movieAdapter = new MovieAdapter(getActivity(), movies);
        gridView.setAdapter(movieAdapter);
        setFirstMovieDetail();


    }
//check if twoPane select first movie
    public void setFirstMovieDetail(){
        if(movieAdapter!= null && MainActivity.mIsTwoPane){
            gridView.setSelection(0);
            movieListener.onMovieClickListener(movies.get(0));
        }
    }


}
