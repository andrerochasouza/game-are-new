package br.com.andre.entity;

import br.com.andre.engine.KeyHandler;
import br.com.andre.panels.GamePanel;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Player extends Entity {
    private static final Logger log = LogManager.getLogger(Player.class);
    private GamePanel gamePanel;
    private KeyHandler keyHandler;
    private ImageView imageView;

    public Player(GamePanel gamePanel, KeyHandler keyHandler, String path){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.imageView = new ImageView(path);
        setupDefaultValues();
    }

    public void setupDefaultValues(){
        this.x = 100;
        this.y = 100;
        this.speed = 5;
    }

    public Node getNode(){
        return this.imageView;
    }

    public void update(){
        if(keyHandler.isUp()){
            this.y -= this.speed;
        }
        if(keyHandler.isDown()){
            this.y += this.speed;
        }
        if(keyHandler.isLeft()){
            this.x -= this.speed;
        }
        if(keyHandler.isRight()){
            this.x += this.speed;
        }
    }

    public void render(){
        if(gamePanel.getChildren().contains(imageView)){
            imageView.setX(this.x);
            imageView.setY(this.y);
        } else {
            imageView.setX(this.x);
            imageView.setY(this.y);
            imageView.setFitHeight(gamePanel.getTileSize());
            imageView.setFitWidth(gamePanel.getTileSize());
            gamePanel.getChildren().add(imageView);
        }

    }

}
