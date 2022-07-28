package com.cubes.komentarapp.data.source.remote.response;

import com.cubes.komentarapp.data.model.Comments;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseComments implements Serializable {

    public int status;
    public String message;
    public ArrayList<Comments> data;

}
