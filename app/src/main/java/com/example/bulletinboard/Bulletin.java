package com.example.bulletinboard;

import java.util.ArrayList;
import java.util.List;

public class Bulletin {

    private String title;
    private String author;
    private String publishTime;
    private int type;
    private int res_id;
    private int images[];

    public Bulletin(String title, String author, String publishTime, int type) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.type = type;
    }

    public Bulletin(String title, String author, String publishTime, int type, int res_id) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.type = type;
        this.res_id = res_id;
    }

    public Bulletin(String title, String author, String publishTime, int type, int[] images) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.type = type;
        this.images = images;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public int getImage() {
        return res_id;
    }

    public int[] getImages() {
        return images;
    }
}
