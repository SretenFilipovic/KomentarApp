package com.cubes.komentarapp.data.model.api;

import com.cubes.komentarapp.data.model.domain.Category;

import java.util.ArrayList;

public class CategoryApi {

    public String type;
    public int id;
    public String name;
    public String color;
    public String main_image;
    public String description;
    public Category subcategory;
    public ArrayList<CategoryApi> subcategories;

}
