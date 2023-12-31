package br.com.andre.terrain;

import javafx.scene.image.ImageView;

public class Tile {

    ImageView imageView;
    boolean isCollidable;

    public Tile(String path, boolean isCollidable){
        this.imageView = new ImageView("file:" + path);
        this.isCollidable = isCollidable;
    }

    public ImageView getImageView(){
        return this.imageView;
    }

    public boolean isCollidable(){
        return this.isCollidable;
    }

}
