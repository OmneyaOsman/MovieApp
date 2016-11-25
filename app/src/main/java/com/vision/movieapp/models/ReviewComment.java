package com.vision.movieapp.models;

/**
 * Created by Vision on 08/11/2016.
 */

public class ReviewComment {

    private String authorName ;
    private String content ;

    public ReviewComment(String authorName, String content) {
        this.authorName = authorName;
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
