package com.example.csc325.csc325.Posts;

import java.util.UUID;

public abstract class Post {
    private String title;
    private String description;

    private String id;

    private long unixTime;

    public Post(){
        this.title ="";
        this.description = "";
        this.id = "pst-"  + UUID.randomUUID().toString();
        this.unixTime = System.currentTimeMillis() / 1000;
    }

    public Post(String title, String description) {
        this.title = title;
        this.description = description;
        this.id = "pst-"  + UUID.randomUUID().toString();
        this.unixTime = System.currentTimeMillis() / 1000;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public long getUnixTime() {
        return unixTime;
    }

    public void setUnixTime(long unixTime) {
        this.unixTime = unixTime;
    }
}
