package com.cubes.komentarapp.data.source.remote.response;

import com.cubes.komentarapp.data.model.Data;

import java.io.Serializable;

public class ResponseNews implements Serializable {

    public int status;
    public String message;
    public Data data;

}
