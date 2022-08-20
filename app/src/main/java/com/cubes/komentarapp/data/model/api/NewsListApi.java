package com.cubes.komentarapp.data.model.api;

import com.cubes.komentarapp.data.model.domain.CategoryBox;
import com.cubes.komentarapp.data.model.domain.News;

import java.util.ArrayList;

public class NewsListApi {

    public PaginationApi pagination;
    public ArrayList<News> news;

    // fildovi za HomePage headNews
    public ArrayList<News> slider;
    public ArrayList<News> top;
    public ArrayList<News> editors_choice;
    public ArrayList<CategoryBox> category;
    public ArrayList<News> most_read;
    public ArrayList<News> latest;
    public ArrayList<News> most_comented;
    public ArrayList<News> videos;

}
