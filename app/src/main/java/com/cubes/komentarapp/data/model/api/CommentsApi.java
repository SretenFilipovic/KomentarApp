package com.cubes.komentarapp.data.model.api;

import com.cubes.komentarapp.data.model.domain.Comments;

import java.util.ArrayList;

public class CommentsApi {

    public int negative_votes;
    public int positive_votes;
    public String news;
    public String name;
    public String created_at;
    public String parent_comment;
    public String id;
    public String content;
    public ArrayList<Comments> children;

}
