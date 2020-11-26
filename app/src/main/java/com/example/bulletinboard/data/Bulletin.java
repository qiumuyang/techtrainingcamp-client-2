package com.example.bulletinboard.data;

import android.graphics.Bitmap;
import android.util.Log;

public class Bulletin {

    private static final String TAG = "Bulletin";

    private String title;
    private String author;
    private String publishTime;
    private String id;
    private int type;
    private Bitmap image;
    private Bitmap[] images;

    public Bulletin(int type, String id, String title, String author, String publishTime) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.type = type;
        this.id = id;
    }

    public Bulletin(int type, String id, String title, String author, String publishTime, Bitmap image) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.type = type;
        this.id = id;
        this.image = image;
    }

    public Bulletin(int type, String id, String title, String author, String publishTime, Bitmap[] images) {
        this.title = title;
        this.author = author;
        this.publishTime = publishTime;
        this.type = type;
        this.id = id;
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getId() { return id; }

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

    public int imageCount() { return images.length; }
}
