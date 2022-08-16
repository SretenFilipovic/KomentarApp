package com.cubes.komentarapp.data.source.remote.response;

import java.util.ArrayList;

public class RequestCommentPost extends ResponseParentClass {

    public ArrayList<String> data;


    public RequestCommentPost(ArrayList<String> data) {
        this.data = data;
    }
}
