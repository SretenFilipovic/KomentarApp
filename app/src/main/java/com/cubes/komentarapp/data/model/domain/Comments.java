package com.cubes.komentarapp.data.model.domain;

import java.util.ArrayList;

public class Comments {

    public int negativeVotes;
    public int positiveVotes;
    public String newsId;
    public String name;
    public String createdAt;
    public String parentCommentId;
    public String id;
    public String content;
    public ArrayList<Comments> children;

    public Vote commentVote;
}
