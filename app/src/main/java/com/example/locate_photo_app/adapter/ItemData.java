package com.example.locate_photo_app.adapter;

import android.graphics.Bitmap;

public class ItemData {

    private String title;
    private Bitmap img;

    public ItemData(String title, Bitmap img){

        this.title = title;
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImage() {
        return img;
    }
}
