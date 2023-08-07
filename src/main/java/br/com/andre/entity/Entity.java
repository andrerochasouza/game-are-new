package br.com.andre.entity;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

import java.util.Objects;


public class Entity {

    public int x, y;
    public int speed;
    public String pathFolderSprites;
    public ImageView imageView;
    public Rectangle solidArea;
    public boolean collisionOn = false;
    public int animationVelocityMillis = 500;

    void toggleImageUp(){
        String[] upImages = {"up-1", "up-2", "up-3", "up-4"};
        toggleImage("up", upImages);
    }

    void toggleImageDown(){
        String[] downImages = {"down-1", "down-2", "down-3", "down-4"};
        toggleImage("down", downImages);
    }

    void toggleImageLeft(){
        String[] leftImages = {"left-1", "left-2", "left-3", "left-4"};
        toggleImage("left", leftImages);
    }

    void toggleImageRight(){
        String[] rightImages = {"right-1", "right-2", "right-3", "right-4"};
        toggleImage("right", rightImages);
    }

    void togleImageStopped(){
        String[] stoppedImages = {"stopped-1", "stopped-2", "stopped-3"};
        toggleImage("stopped", stoppedImages);
    }

    private void toggleImage(String baseName, String[] imageNames) {

            String currentUrl = imageView.getImage().getUrl();

            for (int i = 0; i < imageNames.length; i++) {
                if (currentUrl.contains(imageNames[i])) {
                    int nextIndex = (i + 1) % imageNames.length;
                    String nextImageName = imageNames[nextIndex];
                    imageView.setImage(new Image(pathFolderSprites + "/" + nextImageName + ".png"));
                    break;
                } else if(!Objects.equals(currentUrl, pathFolderSprites + "/" + baseName + "-1.png")) {
                    imageView.setImage(new Image(pathFolderSprites + "/" + baseName + "-1.png"));
                    break;
                }
            }
    }

}
