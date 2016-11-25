package com.vision.movieapp.utils;

import android.util.Log;

import com.vision.movieapp.models.Movie;
import com.vision.movieapp.models.ReviewComment;
import com.vision.movieapp.models.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;



/**
 * Created by Vision on 12/11/2016.
 */

public class ParseJSON {


    private ParseJSON(){}

    /*
    *  method to extract information of each movie  @return list of movies
    * */

    public static ArrayList<Movie> extractMovies(JSONObject rootJSON) {


        ArrayList<Movie> moviesList = new ArrayList<>();
        try {

            JSONArray resultsArray = rootJSON.getJSONArray(Constants.JSON_RESULTS_TAG);

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject resultObject = resultsArray.getJSONObject(i);
                String poster = resultObject.getString(Constants.JSON_POSTER_TAG);
                int movieId = resultObject.getInt(Constants.JSON_ID_TAG);
                String overView = resultObject.getString(Constants.JSON_OVERVIEW_TAG);
                String originalTitle = resultObject.getString(Constants.JSON_ORIGINAL_TITLE_TAG);
                String releaseDate = resultObject.getString(Constants.JSON_RELEASE_DATE_TAG);
                String voteAverage = resultObject.getString(Constants.JSON_VOTE_AVERAGE_TAG);

                moviesList.add(new Movie(poster, originalTitle, overView, releaseDate, voteAverage , movieId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return moviesList;
    }

    /**
     * method to extract author and content of each review*/
    public static ArrayList<ReviewComment> extractComments (JSONObject commentJSON){

        ArrayList<ReviewComment> commentsList = new ArrayList<>();

        try {

            JSONArray jsonArray = commentJSON.getJSONArray(Constants.JSON_RESULTS_TAG);

            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject mJson = jsonArray.getJSONObject(i);
                String author = mJson.getString(Constants.JSON_REVIEWS_AUTHOR_TAG);
                String content = mJson.getString(Constants.JSON_REVIEWS_CONTENT_TAG);


                commentsList.add(new ReviewComment(author, content));
                Log.d(TAG, "extractComments: " +author  );

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return commentsList ;
    }

    /**
     * method to extract key of each trailer*/

    public static ArrayList<Trailer> extractKeys (JSONObject trailersJSON){

        ArrayList<Trailer> keysList = new ArrayList<>();

        try {
            //TODO

            JSONArray jsonArray = trailersJSON.getJSONArray(Constants.JSON_RESULTS_TAG);

            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject mJson = jsonArray.getJSONObject(i);
                String key = mJson.getString(Constants.JSON_VIDEOS_KEY_TAG);
                String name = mJson.getString(Constants.JSON_VIDEOS_NAME_TAG);

                keysList.add(new Trailer(key , name));
                Log.d(TAG, "extractKeys: " +key  );

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return keysList ;

    }


}
