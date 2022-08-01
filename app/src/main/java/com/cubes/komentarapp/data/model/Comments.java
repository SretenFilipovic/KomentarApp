package com.cubes.komentarapp.data.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Comments implements Serializable{

    public int negative_votes;
    public int positive_votes;
    public String news;
    public String name;
    public String created_at;
    public String parent_comment;
    public String id;
    public String content;
    public ArrayList<Comments> children;

    // za proveru da li je vec glasano na datom komentaru
    // pokusao sam sa SharedPreferences da sacuvam izbor, ali nije mi poslo za rukom
    // trenutno resenje funkcionise dok se korisnik nalazi u CommentsActivity, ali cim izadje i vrati se, ponovo moze da glasa
    public boolean isVoted = false;


}
