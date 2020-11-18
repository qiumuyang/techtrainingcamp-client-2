package com.example.bulletinboard;

public class BulletinJson {
    private int type;
    private String id;
    private String title;
    private String author;
    private String publishTime;
    private String cover;
    private String[] covers;

    public int getType() {
        return type;
    }

    public String getId() {
        return id;
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

    public String getCover() {
        return cover;
    }

    public String[] getCovers() {
        return covers;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setCovers(String[] covers) {
        this.covers = covers;
    }
}
