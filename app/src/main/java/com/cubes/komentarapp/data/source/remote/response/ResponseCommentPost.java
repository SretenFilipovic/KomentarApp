package com.cubes.komentarapp.data.source.remote.response;

import java.io.Serializable;
import java.util.ArrayList;

// U dokumentaciji na Postman-u pise da "data" komentara koji se sastavlja predstavlja ArrayList
// buduci da se traze tekstualna polja news, reply_id, name, email i content pretpostavio sam da se radi o ArrayList stringova
// Napravio sam ovu model klasu da se poklapa u skladu sa tim
// konstruktor je tu da bi mogao da napravim Body objekta koji predstavlja komentar koji se salje na server
// Kreiranje i slanje komentara se nalayi u klasama PostCommentActivity i PostReplyActivity
// u nekom tutorijalu sam video da u @POST pozivima treba primitivne tipove podataka pretvoriti u kompleksne pa sam status promenio iz int u Integer

public class ResponseCommentPost extends ResponseParentClass implements Serializable {

    public ArrayList<String> data;

    public ResponseCommentPost(ArrayList<String> data) {
        this.data = data;
    }
}
