package com.cubes.komentarapp.data.model.domain;

import com.cubes.komentarapp.data.model.api.CategoryApi;

public class News {

    public int id;
    public String image;
    public CategoryApi category;
    public String title;
    public String created_at;

}
