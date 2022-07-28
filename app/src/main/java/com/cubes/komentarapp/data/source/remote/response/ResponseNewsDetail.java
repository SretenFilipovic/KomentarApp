package com.cubes.komentarapp.data.source.remote.response;

import com.cubes.komentarapp.data.model.News;

import java.io.Serializable;

public class ResponseNewsDetail implements Serializable {

    public int status;
    public String message;
    public News data;

}
