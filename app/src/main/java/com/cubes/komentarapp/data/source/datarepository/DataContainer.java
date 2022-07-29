package com.cubes.komentarapp.data.source.datarepository;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.data.model.Comments;

import java.util.ArrayList;

public class DataContainer {

    // BaseUrl koji se koristi kod pozivanja servera
    public static final String BASE_URL = "https://komentar.rs/wp-json/";
    public static final int page = 1;

    // staticka lista koju popunjavamo listom kategorija koju dobijemo sa servera
    public static ArrayList<Category> categoryList = new ArrayList<>();

}
