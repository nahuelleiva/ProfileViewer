package com.example.profileviewer;

import android.graphics.Bitmap;

public class ImageItem {
    private Bitmap image;
    private int idImage;

    public ImageItem(Bitmap image, int idImage) {
        super();
        this.image = image;
        this.idImage = idImage;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getIdImage() { return idImage; }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setId(int id_) { this.idImage = id_; }

}
