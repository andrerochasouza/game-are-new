package br.com.andre.entity;

import br.com.andre.engine.CollisionChecker;
import br.com.andre.engine.KeyHandler;
import br.com.andre.panels.GamePanel;
import br.com.andre.terrain.TileManager;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Player extends Entity {
    private static final Logger log = LogManager.getLogger(Player.class);
    private GamePanel gamePanel;
    private KeyHandler keyHandler;
    private long lastUpdateTime = System.currentTimeMillis();
    private KeyCode lastKeyCode = null;


    public Player(GamePanel gamePanel, KeyHandler keyHandler, int animationVelocityMillis, String pathFolderSprites){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.pathFolderSprites = pathFolderSprites;
        this.imageView = new ImageView(pathFolderSprites + "/down-1.png");
        this.animationVelocityMillis = animationVelocityMillis;
        this.solidArea = new Rectangle(10, 20, 44, 44);
        setupDefaultValues();
    }

    public Player(GamePanel gamePanel, KeyHandler keyHandler, String pathFolderSprites){
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        this.pathFolderSprites = pathFolderSprites;
        this.imageView = new ImageView(pathFolderSprites + "/down-1.png");
        this.solidArea = new Rectangle(10, 20, 44, 44);
        setupDefaultValues();
    }

    public void setupDefaultValues(){
        this.x = (gamePanel.getScreenWidth() / 2) - (gamePanel.getTileSize() / 2);
        this.y = (gamePanel.getScreenHeight() / 2) - (gamePanel.getTileSize() / 2);
        this.speed = 5;
    }

    public Node getNode(){
        return this.imageView;
    }

    public void update(TileManager[] tileManagers){
        long currentTime = System.currentTimeMillis();
        long deltaTime = currentTime - lastUpdateTime;

        if (keyHandler.isUp()) {
            y -= speed;
            if(this.lastKeyCode != KeyCode.UP){
                toggleImageUp();
            } else if(deltaTime >= animationVelocityMillis){
                toggleImageUp();
                lastUpdateTime = currentTime;
            }
            this.lastKeyCode = KeyCode.UP;
        }

        if (keyHandler.isDown()) {
            y += speed;
            if(this.lastKeyCode != KeyCode.DOWN){
                toggleImageDown();
            } else if(deltaTime >= animationVelocityMillis){
                toggleImageDown();
                lastUpdateTime = currentTime;
            }
            this.lastKeyCode = KeyCode.DOWN;
        }

        if (keyHandler.isLeft()) {
            x -= speed;
            if(this.lastKeyCode != KeyCode.LEFT){
                toggleImageLeft();
            } else if(deltaTime >= animationVelocityMillis){
                toggleImageLeft();
                lastUpdateTime = currentTime;
            }
            this.lastKeyCode = KeyCode.LEFT;
        }

        if (keyHandler.isRight()) {
            x += speed;
            if(this.lastKeyCode != KeyCode.RIGHT){
                toggleImageRight();
            } else if(deltaTime >= animationVelocityMillis){
                toggleImageRight();
                lastUpdateTime = currentTime;
            }
            this.lastKeyCode = KeyCode.RIGHT;
        }

        if(!keyHandler.isUp() && !keyHandler.isDown() && !keyHandler.isLeft() && !keyHandler.isRight()){
            if(this.lastKeyCode != null){
                togleImageStopped();
            } else if(deltaTime >= animationVelocityMillis){
                togleImageStopped();
                lastUpdateTime = currentTime;
            }
            this.lastKeyCode = null;
        }

        collisionOn = false;
        CollisionChecker collisionChecker = new CollisionChecker(gamePanel, tileManagers);
        if(lastKeyCode != null) collisionChecker.checkTile(this, lastKeyCode);
        if(collisionOn) {
            if (lastKeyCode == KeyCode.UP) {
                y += speed;
            } else if (lastKeyCode == KeyCode.DOWN) {
                y -= speed;
            } else if (lastKeyCode == KeyCode.LEFT) {
                x += speed;
            } else if (lastKeyCode == KeyCode.RIGHT) {
                x -= speed;
            }
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
