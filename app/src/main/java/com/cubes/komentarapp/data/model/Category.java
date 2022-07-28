package com.cubes.komentarapp.data.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    public String type;
    public int id;
    public String name;
    public String color;
    public String main_image;
    public String description;
    public Category subcategory;
    public ArrayList<Category> subcategories;

}
