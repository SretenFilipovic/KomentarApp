package com.cubes.komentarapp.data.model;

import java.io.Serializable;
import java.util.ArrayList;

public class News implements  Serializable{

    public int id;
    public String image;
    public String image_source;
    public String author_name;
    public String source;
    public Category category;
    public String title;
    public String description;
    public int comment_enabled;
    public int comments_count;
    public int shares_count;
    public String created_at;
    public String url;
    public String click_type;

    // fildovi potrebni za NewsDetailsActivity
    public ArrayList<Tags> tags;
    public ArrayList<News> related_news;
    public ArrayList<News> category_news;
    public ArrayList<Comments> comments_top_n;


}
