package com.cubes.komentarapp.data.model.domain;

public class MyNews {

    public int id;
    public String title;

    public MyNews(int newsId, String newsTitle) {
        this.id = newsId;
        this.title = newsTitle;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
