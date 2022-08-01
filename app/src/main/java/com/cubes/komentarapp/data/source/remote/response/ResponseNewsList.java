package com.cubes.komentarapp.data.source.remote.response;

import com.cubes.komentarapp.data.model.NewsData;

import java.io.Serializable;

public class ResponseNewsList extends ResponseParentClass implements Serializable {

    public NewsData data;

}
