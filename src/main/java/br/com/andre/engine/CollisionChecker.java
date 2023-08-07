package br.com.andre.engine;

import br.com.andre.entity.Entity;
import br.com.andre.panels.GamePanel;
import br.com.andre.terrain.Tile;
import br.com.andre.terrain.TileManager;
import javafx.scene.input.KeyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CollisionChecker {

    private static final Logger log = LogManager.getLogger(CollisionChecker.class);
    private GamePanel gamePanel;
    private TileManager[] tileManagers;

    public CollisionChecker(GamePanel gamePanel, TileManager[] tileManagers){
        this.gamePanel = gamePanel;
        this.tileManagers = tileManagers;
    }

    public void checkTile(Entity entity, KeyCode keyCode){

        int entityTopY = (int) (entity.y + entity.solidArea.getY());
        log.debug("entityTopY: " + entityTopY);
        int entityLeftX = (int) (entity.x + entity.solidArea.getX());
        log.debug("entityLeftX: " + entityLeftX);
        int entityRightX = (int) ((entity.x + gamePanel.getTileSize()) - entity.solidArea.getX());
        log.debug("entityRightX: " + entityRightX);
        int entityBottomY = entity.y + gamePanel.getTileSize();
        log.debug("entityBottomY: " + entityBottomY);

        Tile[] tiles = new Tile[tileManagers.length];

        switch (keyCode){
            case UP:
                for (int i = 0; i < tileManagers.length; i++) {
                    tiles[i] = tileManagers[i].getTile(entityLeftX, entityTopY);
                    tiles[i] = tileManagers[i].getTile(entityRightX, entityTopY);
                }

                for (Tile tile: tiles){
                    if(tile == null){
                        continue;
                    }
                    if(tile.isCollidable()){
                        entity.collisionOn = true;
                    }
                }
                break;
            case DOWN:
                for (int i = 0; i < tileManagers.length; i++) {
                    tiles[i] = tileManagers[i].getTile(entityLeftX, entityBottomY);
                    tiles[i] = tileManagers[i].getTile(entityRightX, entityBottomY);
                }

                for (Tile tile: tiles){
                    if(tile == null){
                        continue;
                    }
                    if(tile.isCollidable()){
                        entity.collisionOn = true;
                    }
                }

                break;
            case LEFT:
                for (int i = 0; i < tileManagers.length; i++) {
                    tiles[i] = tileManagers[i].getTile(entityLeftX, entityTopY);
                    tiles[i] = tileManagers[i].getTile(entityLeftX, entityBottomY);
                }

                for (Tile tile: tiles){
                    if(tile == null){
                        continue;
                    }
                    if(tile.isCollidable()){
                        entity.collisionOn = true;
                    }
                }

                break;
            case RIGHT:
                for (int i = 0; i < tileManagers.length; i++) {
                    tiles[i] = tileManagers[i].getTile(entityRightX, entityTopY);
                    tiles[i] = tileManagers[i].getTile(entityRightX, entityBottomY);
                }

                for (Tile tile: tiles){
                    if(tile == null){
                        continue;
                    }
                    if(tile.isCollidable()){
                        entity.collisionOn = true;
                    }
                }

                break;
        }
    }
}
