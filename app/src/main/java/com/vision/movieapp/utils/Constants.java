package com.vision.movieapp.utils;

/**
 * Created by Vision on 05/10/2016.
 */

public class Constants {

    private Constants() {
    }

    public static final String BASE_URL_POSTER = "http://image.tmdb.org/t/p/";
    public static final String MOVIE_DB_URL = "http://api.themoviedb.org/3/movie";
    public static final String API_PARAM = "api_key";
    public static final String POSTER_SMALL_SIZE = "w185";
    public static final String REVIEWS= "reviews";
    public static final String VIDEOS = "videos";

    //... JSON tags helps to extract data
    public static final String JSON_RESULTS_TAG = "results";
    public static final String JSON_POSTER_TAG = "poster_path";
    public static final String JSON_OVERVIEW_TAG = "overview";
    public static final String JSON_RELEASE_DATE_TAG = "release_date";
    public static final String JSON_ORIGINAL_TITLE_TAG = "original_title";
    public static final String JSON_VOTE_AVERAGE_TAG = "vote_average";
    public static final String JSON_ID_TAG = "id";

    public static final String JSON_REVIEWS_AUTHOR_TAG = "author";
    public static final String JSON_REVIEWS_CONTENT_TAG = "content";


    public static final String JSON_VIDEOS_KEY_TAG = "key";
    public static final String JSON_VIDEOS_NAME_TAG = "name";
}
