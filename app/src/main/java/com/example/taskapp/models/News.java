package com.example.taskapp.models;

import java.io.Serializable;

public class News implements Serializable {

    private String title;
    private String date;

    public News(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
