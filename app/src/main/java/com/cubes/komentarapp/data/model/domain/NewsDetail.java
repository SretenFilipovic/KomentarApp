package com.cubes.komentarapp.data.model.domain;

import java.util.ArrayList;

public class NewsDetail {

    public int id;
    public boolean comment_enabled;
    public int comments_count;
    public String url;
    public ArrayList<Tags> tags;
    public ArrayList<News> related_news;
    public ArrayList<Comments> comments_top_n;


}
