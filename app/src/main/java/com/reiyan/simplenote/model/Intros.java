package com.reiyan.simplenote.model;

public class Intros {

    private int images;
    private String desc;

    public Intros(int images, String desc) {
        this.images = images;
        this.desc = desc;
    }

    public int getImages() {
        return images;
    }

    public String getDesc() {
        return desc;
    }
}
