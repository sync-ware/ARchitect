package com.sync.architect;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;

public class Floorplan {

    private String name;
    private Drawable image;

    public Floorplan(String name, Drawable image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public Drawable getImage() { return image; }
}
