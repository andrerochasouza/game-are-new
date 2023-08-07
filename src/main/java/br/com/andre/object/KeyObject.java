package br.com.andre.object;

import br.com.andre.panels.GamePanel;

public class KeyObject extends SuperObject{

    public KeyObject(GamePanel gamePanel, int levelObject, boolean animationActive, int x, int y){
        super(gamePanel, ObjectEnum.KEY, levelObject, animationActive, x, y);
    }
}
