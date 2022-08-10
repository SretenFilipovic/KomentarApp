package com.cubes.komentarapp.data.source.remote.response;

import java.util.ArrayList;

public class ResponseCommentPost extends ResponseParentClass {

    public ArrayList<String> data;


    public ResponseCommentPost(ArrayList<String> data) {
        this.data = data;
    }
}
