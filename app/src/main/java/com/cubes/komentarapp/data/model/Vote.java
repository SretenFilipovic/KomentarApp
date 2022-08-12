package com.cubes.komentarapp.data.model;

public class Vote {

    public String commentId;
    public boolean vote;

    public Vote(String commentId, boolean vote) {
        this.commentId = commentId;
        this.vote = vote;
    }
}
