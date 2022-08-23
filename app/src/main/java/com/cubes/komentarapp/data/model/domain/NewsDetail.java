package com.cubes.komentarapp.data.model.domain;

import java.util.ArrayList;

public class NewsDetail {

    public int id;
    public boolean commentEnabled;
    public int commentsCount;
    public String url;
    public ArrayList<Tags> tags;
    public ArrayList<News> relatedNews;
    public ArrayList<Comments> topComments;
}
