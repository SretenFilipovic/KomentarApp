package com.cubes.komentarapp.data.model;

import java.util.ArrayList;

public class Category {

    public String type;
    public int id;
    public String name;
    public String color;
    public String main_image;
    public String description;
    public Category subcategory;
    public ArrayList<Category> subcategories;

}
