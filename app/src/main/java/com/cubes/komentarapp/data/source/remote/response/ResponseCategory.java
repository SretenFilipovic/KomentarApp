package com.cubes.komentarapp.data.source.remote.response;

import com.cubes.komentarapp.data.model.Category;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseCategory implements Serializable {

    public int status;
    public String message;
    public ArrayList<Category> data;

}
