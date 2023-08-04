package br.com.andre.engine;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KeyHandler {

    private static final Logger log = LogManager.getLogger(KeyHandler.class);
    private boolean up, down, left, right, space, escape = false;
    private Scene gameScene;

    public KeyHandler(Scene gameScene){
        this.gameScene = gameScene;
        setupKeyHandler();
    }

    private void setupKeyHandler(){
        gameScene.setOnKeyPressed(event -> {
            KeyCode keyCode = event.getCode();
            handleKeyPress(keyCode);
        });

        gameScene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            handleKeyRelease(keyCode);
        });
    }

    private void handleKeyPress(KeyCode keyCode) {
        switch (keyCode) {
            case UP:
                up = true;
                break;
            case DOWN:
                down = true;
                break;
            case LEFT:
                left = true;
                break;
            case RIGHT:
                right = true;
                break;
            case SPACE:
                space = true;
                break;
            case ESCAPE:
                escape = true;
                break;
        }
        log.info("Key pressed: " + keyCode);
    }

    private void handleKeyRelease(KeyCode keyCode) {
        switch (keyCode) {
            case UP:
                up = false;
                break;
            case DOWN:
                down = false;
                break;
            case LEFT:
                left = false;
                break;
            case RIGHT:
                right = false;
                break;
            case SPACE:
                space = false;
                break;
            case ESCAPE:
                escape = false;
                break;
        }
    }

    public boolean isUp() {
        return up;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isSpace() {
        return space;
    }

    public boolean isEscape() {
        return escape;
    }
}
