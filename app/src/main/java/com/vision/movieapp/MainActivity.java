package com.vision.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.vision.movieapp.fragments.DetailMovieFragment;
import com.vision.movieapp.fragments.MovieFragment;
import com.vision.movieapp.models.Movie;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity  implements MovieClickListener{

    boolean mIsTwoPane = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.movies);
        setSupportActionBar(toolbar);

        MovieFragment movieFragment = new MovieFragment();

        //set the activity to be listener of the fragment
        movieFragment.setMovieListener(this);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.movie_main_fragment, movieFragment)
                .commit();

        //check if Twopane
        if(null != findViewById(R.id.detail_movie_fragment)){
            mIsTwoPane = true;
            //TODO open first Movie Details
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieClickListener(Movie movie) {

        //case onePane
        //start DetailActivity
        if(!mIsTwoPane){
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("current Movie", Parcels.wrap(movie));
            startActivity(intent);
        }else
        {
            DetailMovieFragment detailMovieFragment = new DetailMovieFragment();
            Bundle extras = new Bundle();
            extras.putParcelable("current Movie" ,Parcels.wrap(movie));
            detailMovieFragment.setArguments(extras);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace( R.id.detail_movie_fragment ,detailMovieFragment)
                    .commit();
        }


    }
}
