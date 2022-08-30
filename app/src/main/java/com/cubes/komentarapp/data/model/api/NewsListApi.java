package com.cubes.komentarapp.data.model.api;

import java.util.ArrayList;

public class NewsListApi {

    public PaginationApi pagination;
    public ArrayList<NewsApi> news;

    // fildovi za HomePage headNews
    public ArrayList<NewsApi> slider;
    public ArrayList<NewsApi> top;
    public ArrayList<NewsApi> editors_choice;
    public ArrayList<CategoryBoxApi> category;
    public ArrayList<NewsApi> most_read;
    public ArrayList<NewsApi> latest;
    public ArrayList<NewsApi> most_comented;
    public ArrayList<NewsApi> videos;

}
