package com.cubes.komentarapp.data.source.remote.response;

import com.cubes.komentarapp.data.model.Category;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseCategoryList extends ResponseParentClass implements Serializable {

    public ArrayList<Category> data;

}
