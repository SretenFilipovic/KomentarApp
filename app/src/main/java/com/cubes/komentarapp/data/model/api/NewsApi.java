package com.cubes.komentarapp.data.model.api;

import com.cubes.komentarapp.data.model.domain.Category;
import com.cubes.komentarapp.data.model.domain.Comments;
import com.cubes.komentarapp.data.model.domain.News;
import com.cubes.komentarapp.data.model.domain.Tags;

import java.util.ArrayList;

public class NewsApi {

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

    // fildovi potrebni za NewsDetails
    public ArrayList<Tags> tags;
    public ArrayList<News> related_news;
    public ArrayList<News> category_news;
    public ArrayList<Comments> comments_top_n;


}
