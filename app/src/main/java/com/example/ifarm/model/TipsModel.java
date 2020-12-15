package com.example.ifarm.model;

public class TipsModel {
    int imageID;
    String title;
    String description;

    public TipsModel(String title, String description, int imageID) {
        this.imageID = imageID;
        this.title = title;
        this.description = description;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
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
}
