package com.example.bulletinboard;

import android.graphics.Bitmap;
import android.util.Log;

public class Bulletin {

    private static final String TAG = "Bulletin";

    private String title;
    private String author;
    private String publishTime;
    private int type;
    private Bitmap image;
    private Bitmap[] images;

    public Bulletin(int type, String title, String author, String publishTime) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.type = type;
    }

    public Bulletin(int type, String title, String author, String publishTime, Bitmap image) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.type = type;
        this.image = image;
    }

    public Bulletin(int type, String title, String author, String publishTime, Bitmap[] images) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.type = type;
        this.images = images;
    }

    public int getType() {
        return type;
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

    public Bitmap getImage() { return image; }

    public Bitmap getImages(int index) {
        return images[index];
    }
}
