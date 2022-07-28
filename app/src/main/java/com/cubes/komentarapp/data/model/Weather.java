package com.cubes.komentarapp.data.model;

import java.io.Serializable;

public class Weather implements Serializable {

    public String id;
    public String name;
    public String temp;
    public String temp_min;
    public String temp_max;
    public String wind;
    public String pressure;
    public int humidity;
    public String description;
    public String icon_url;
    public Weather day_0;
    public Weather day_1;
    public Weather day_2;
    public Weather day_3;
    public Weather day_4;
    public Weather day_5;
    public Weather day_6;

}
