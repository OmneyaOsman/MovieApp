package com.vision.movieapp.models;

import org.parceler.Parcel;

import io.realm.MovieRealmProxy;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Vision on 05/10/2016.
 */
@Parcel(implementations = { MovieRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { Movie.class })

public class Movie extends RealmObject  {

    @PrimaryKey
    private int mId ;
    private String mImageResource;
    private String mOverView;
    private String mReleaseDate;
    private String mOriginalTitle;
    private String mVoteAverage;
    private int favorite;

    public Movie(){}


    public Movie(String mImageResource, String mOriginalTitle, String mOverView, String mReleaseDate, String mVoteAverage, int mId) {
        this.mImageResource = mImageResource;
        this.mOverView = mOverView;
        this.mReleaseDate = mReleaseDate;
        this.mOriginalTitle = mOriginalTitle;
        this.mVoteAverage = mVoteAverage;
        this.mId = mId ;

    }



    public String getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(String mImageResource) {
        this.mImageResource = mImageResource;
    }

    public String getmOverView() {
        return mOverView;
    }

    public void setmOverView(String mOverView) {
        this.mOverView = mOverView;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getmOriginalTitle() {
        return mOriginalTitle;
    }

    public void setmOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmVoteAverage() {
        return mVoteAverage;
    }

    public void setmVoteAverage(String mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
