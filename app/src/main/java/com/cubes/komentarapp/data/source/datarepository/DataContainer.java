package com.cubes.komentarapp.data.source.datarepository;

import com.cubes.komentarapp.data.model.Category;
import com.cubes.komentarapp.data.model.Comments;

import java.util.ArrayList;

public class DataContainer {

    // BaseUrl koji se koristi kod pozivanja servera
    public static final String BASE_URL = "https://komentar.rs/wp-json/";

    // staticka lista koju popunjavamo listom kategorija koju dobijemo sa servera
    public static ArrayList<Category> categoryList = new ArrayList<>();

    // staticka lista koju popunjavamo listom komentara za izabranu vest
    public static ArrayList<Comments> commentList = new ArrayList<>();


}
