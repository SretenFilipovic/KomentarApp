package com.cubes.komentarapp.data.model;

import java.util.ArrayList;

public class Comments {

    public int negative_votes;
    public int positive_votes;
    public String news;
    public String name;
    public String created_at;
    public String parent_comment;
    public String id;
    public String content;
    public ArrayList<Comments> children;

    // za proveru da li je vec glasano na datom komentaru
    public boolean isVoted = false;


}
