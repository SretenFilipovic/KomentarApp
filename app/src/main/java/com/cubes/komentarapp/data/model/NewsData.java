package com.cubes.komentarapp.data.model;

import java.util.ArrayList;

public class NewsData {

    public Pagination pagination;
    public ArrayList<News> news;

    public ArrayList<News> slider;
    public ArrayList<News> top;
    public ArrayList<News> editors_choice;
    public ArrayList<CategoryHomePage> category;
    public ArrayList<News> most_read;
    public ArrayList<News> latest;
    public ArrayList<News> most_comented; // imaju slovnu gresku sa backend strane
    public ArrayList<News> videos;

}
