package br.com.andre.engine;

import br.com.andre.entity.Player;
import br.com.andre.panels.GamePanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Camera {

    private static final Logger log = LogManager.getLogger(Camera.class);
    private GamePanel gamePanel;
    private int screenX, screenY;

    public Camera(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void update(Player player){
        int characterX = player.x;
        int characterY = player.y;

        int cameraX = gamePanel.getScreenWidth() / 2 - (gamePanel.getTileSize() / 2);
        int cameraY = gamePanel.getScreenHeight() / 2 - (gamePanel.getTileSize() / 2);

        screenX = cameraX - characterX;
        screenY = cameraY - characterY;

        gamePanel.setLayoutX(screenX);
        gamePanel.setLayoutY(screenY);
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

}
