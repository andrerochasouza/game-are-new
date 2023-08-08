package br.com.andre.engine;

import br.com.andre.object.ObjectEnum;
import br.com.andre.object.SuperObject;
import br.com.andre.panels.GamePanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AssetControlObject {

    private GamePanel gamePanel;
    private HashMap<Integer, SuperObject> objectHashMap = new HashMap<>();

    public AssetControlObject(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void render(){
        for (SuperObject superObject : objectHashMap.values()){
            if(superObject.getImageView().getParent() == null){
                gamePanel.getChildren().add(superObject.getImageView());
            } else {
                superObject.getImageView().relocate(superObject.getX(), superObject.getY());
            }
        }
    }

    public void update(ObjectEnum... objectEnums) {
        for (ObjectEnum objectEnum : objectEnums) {
            for (SuperObject superObject : objectHashMap.values()) {
                if (superObject.getObjectEnum().equals(objectEnum) && superObject.animationTimer()) {
                    int yOffset = superObject.isAnimationReference() ? 5 : -5;
                    superObject.setY(superObject.getY() + yOffset);
                    superObject.turnAnimationReference();
                }
            }
        }
    }

    public void addObject(ObjectEnum objectEnum, int levelObject, boolean animationActive, int x, int y){
        SuperObject superObject = new SuperObject(gamePanel, objectEnum, levelObject, animationActive, x, y);
        objectHashMap.put(objectHashMap.size(), superObject);
    }

    public SuperObject getObjectByIndex(int index){
        return objectHashMap.get(index);
    }

    public List<SuperObject> getObjectByObjectEnum(ObjectEnum objectEnum){
        List<SuperObject> superObjects = new ArrayList<>();

        for (SuperObject superObject : objectHashMap.values()){
            if (superObject.getObjectEnum().equals(objectEnum)){
                superObjects.add(superObject);
            }
        }

        return superObjects;
    }

    public void removeObjectByIndex(int index){
        objectHashMap.remove(index);
    }

    public void removeObjectByObjectEnum(ObjectEnum objectEnum){
        List<Integer> indexToRemove = new ArrayList<>();

        for (int index : objectHashMap.keySet()){
            if (objectHashMap.get(index).getObjectEnum().equals(objectEnum)){
                indexToRemove.add(index);
            }
        }

        for (int index : indexToRemove){
            objectHashMap.remove(index);
        }
    }

}
