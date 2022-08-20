package com.cubes.komentarapp.data.model.domain;

import com.cubes.komentarapp.data.model.api.CategoryBoxApi;
import com.cubes.komentarapp.data.model.api.NewsApi;
import com.cubes.komentarapp.data.model.api.PaginationApi;

import java.util.ArrayList;

public class NewsList {

    public ArrayList<News> news;


    public ArrayList<News> slider;
    public ArrayList<News> top;
    public ArrayList<News> editors_choice;
    public ArrayList<CategoryBox> category;
    public ArrayList<News> most_read;
    public ArrayList<News> latest;
    public ArrayList<News> most_comented;
    public ArrayList<News> videos;
}
