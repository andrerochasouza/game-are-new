package br.com.andre.engine;

import br.com.andre.entity.DirectionEnum;
import br.com.andre.entity.Entity;
import br.com.andre.terrain.Tile;
import br.com.andre.terrain.TileManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CollisionChecker {

    private TileManager[] tileManagers;

    public CollisionChecker(TileManager[] tileManagers){
        this.tileManagers = tileManagers;
    }

    public List<DirectionEnum> checkTilesToTraverse(Entity entity){

        int entityTopY = (int) (entity.y + entity.solidArea.getY());
        int entityLeftX = (int) (entity.x + entity.solidArea.getX());
        int entityRightX = (int) (entity.x + entity.solidArea.getX() + entity.solidArea.getWidth());
        int entityBottomY = (int) (entity.y + entity.solidArea.getY() + entity.solidArea.getHeight());

        int[] middleTopLeftBot = {entityLeftX, entityTopY + (entityBottomY - entityTopY) / 2};
        int[] middleTopRightBot = {entityRightX, entityTopY + (entityBottomY - entityTopY) / 2};
        int[] middleLeftTopRight = {entityLeftX + (entityRightX - entityLeftX) / 2, entityTopY};
        int[] middleLeftBotRight = {entityLeftX + (entityRightX - entityLeftX) / 2, entityBottomY};


        List<DirectionEnum> nonTraversableDirection = new ArrayList<>();

        for (TileManager tileManager: tileManagers) {
            Tile tileTopLeft = tileManager.getTile(entityLeftX, entityTopY);
            Tile tileTopRight = tileManager.getTile(entityRightX, entityTopY);
            Tile tileBottomLeft = tileManager.getTile(entityLeftX, entityBottomY);
            Tile tileBottomRight = tileManager.getTile(entityRightX, entityBottomY);

            if (isCollide(new Tile[]{tileTopLeft, tileTopRight}, middleLeftTopRight)) {
                nonTraversableDirection.add(DirectionEnum.UP);
            }

            if (isCollide(new Tile[]{tileTopRight, tileBottomRight}, middleTopRightBot)) {
                nonTraversableDirection.add(DirectionEnum.RIGHT);
            }

            if (isCollide(new Tile[]{tileTopLeft, tileBottomLeft}, middleTopLeftBot)) {
                nonTraversableDirection.add(DirectionEnum.LEFT);
            }

            if (isCollide(new Tile[]{tileBottomLeft, tileBottomRight}, middleLeftBotRight)) {
                nonTraversableDirection.add(DirectionEnum.DOWN);
            }
        }

        nonTraversableDirection = nonTraversableDirection.stream().distinct().collect(Collectors.toList());

        return this.acepptedDirections(nonTraversableDirection);
    }

    private boolean isCollide(Tile[] tiles, int[] middleCollide){
        for (Tile tile: tiles) {
            if(tile != null && tile.isCollidable()){
                if(tile.getImageView().contains(middleCollide[0], middleCollide[1])){
                    return true;
                }
            }
        }
        return false;
    }

    private List<DirectionEnum> acepptedDirections(List<DirectionEnum> nonTraversableDirection){
        List<DirectionEnum> acceptedDirections = new ArrayList<>();
        if(!nonTraversableDirection.contains(DirectionEnum.UP)){
            acceptedDirections.add(DirectionEnum.UP);
        }
        if(!nonTraversableDirection.contains(DirectionEnum.RIGHT)){
            acceptedDirections.add(DirectionEnum.RIGHT);
        }
        if(!nonTraversableDirection.contains(DirectionEnum.LEFT)){
            acceptedDirections.add(DirectionEnum.LEFT);
        }
        if(!nonTraversableDirection.contains(DirectionEnum.DOWN)){
            acceptedDirections.add(DirectionEnum.DOWN);
        }
        return acceptedDirections;
    }
}
