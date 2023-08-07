package br.com.andre.object;

import br.com.andre.panels.GamePanel;
import javafx.scene.image.ImageView;

public class SuperObject {
    private GamePanel gamePanel;
    private ObjectEnum objectEnum;
    private int levelObject;
    private ImageView imageView;
    private boolean animationActive = true;
    private boolean animationReference = false;
    private int animationVelocityMillis = 500;
    private long lastAnimationMillis = 0;
    private int x, y;

    public SuperObject(GamePanel gamePanel, ObjectEnum objEnum, int levelObject, boolean animationActive, int x, int y){
        this.gamePanel = gamePanel;
        this.objectEnum = objEnum;
        this.levelObject = levelObject;
        this.animationActive = animationActive;
        this.x = x;
        this.y = y;
        addImageViewByEnum();
    }

    public boolean animationTimer(){
        if(animationActive){
            long currentMillis = System.currentTimeMillis();
            if(currentMillis - lastAnimationMillis > animationVelocityMillis){
                lastAnimationMillis = currentMillis;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void addImageViewByEnum(){
        try{
            this.imageView = new ImageView(objectEnum.getPath() + levelObject + ".png");
            imageView.setX(x);
            imageView.setY(y);
            imageView.setFitHeight(gamePanel.getTileSize());
            imageView.setFitWidth(gamePanel.getTileSize());
        } catch (RuntimeException e){
            System.out.println("Object Level not found: Level " + levelObject);
            e.printStackTrace();
        }
    }

    public GamePanel getGamePanel(){
        return this.gamePanel;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ObjectEnum getObjectEnum(){
        return this.objectEnum;
    }

    public boolean isAnimationActive() {
        return animationActive;
    }

    public void setAnimationActive(boolean animationActive) {
        this.animationActive = animationActive;
    }

    public boolean isAnimationReference() {
        return animationReference;
    }

    public void setAnimationReference(boolean animationReference) {
        this.animationReference = animationReference;
    }

    public void setAnimationVelocityMillis(int animationVelocityMillis) {
        this.animationVelocityMillis = animationVelocityMillis;
    }

    public void turnAnimationReference(){
        this.animationReference = !this.animationReference;
    }
}
