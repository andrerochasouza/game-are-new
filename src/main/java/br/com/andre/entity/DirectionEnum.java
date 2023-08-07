package br.com.andre.entity;

public enum DirectionEnum {

    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right");


    private String direction;

    DirectionEnum(String direction){
        this.direction = direction;
    }

    public String getDirection(){
        return this.direction;
    }

}
