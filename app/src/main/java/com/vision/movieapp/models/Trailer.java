package com.vision.movieapp.models;

/**
 * Created by Vision on 17/11/2016.
 */

public class Trailer {

    private String key ;
    private String name ;

    public Trailer(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
