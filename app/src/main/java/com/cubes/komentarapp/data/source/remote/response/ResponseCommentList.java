package com.cubes.komentarapp.data.source.remote.response;

import com.cubes.komentarapp.data.model.Comments;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseCommentList extends ResponseParentClass implements Serializable {

    public ArrayList<Comments> data;

}
