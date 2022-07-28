package com.cubes.komentarapp.data.source.remote.response;

import com.cubes.komentarapp.data.model.Horoscope;

import java.io.Serializable;

public class ResponseHoroscope implements Serializable {

    public int status;
    public String message;
    public Horoscope data;

}
