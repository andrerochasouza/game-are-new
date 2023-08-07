package br.com.andre.object;

public enum ObjectEnum {

    KEY(1, "Key", true, "file:src/main/resources/sprites/Objects/Key/key-"),
    DOOR(2, "Paper", true, "file:src/main/resources/sprites/Objects/Paper/paper-"),
    CHEST(3, "Chest", true, "file:src/main/resources/sprites/Objects/Chest/chest-");

    private int id;
    private String name;
    private boolean collision;
    private String path;

    ObjectEnum(int id, String name, boolean collision, String path){
        this.id = id;
        this.name = name;
        this.collision = collision;
        this.path = path;
    }

    public int getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public boolean getCollision(){
        return this.collision;
    }

    public String getPath(){
        return this.path;
    }
}
