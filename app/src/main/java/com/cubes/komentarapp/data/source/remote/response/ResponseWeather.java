package com.cubes.komentarapp.data.source.remote.response;

import com.cubes.komentarapp.data.model.Weather;

import java.io.Serializable;

public class ResponseWeather implements Serializable {

    public int status;
    public String message;
    public Weather data;

}
