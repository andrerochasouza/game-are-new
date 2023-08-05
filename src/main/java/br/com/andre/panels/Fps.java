package br.com.andre.panels;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Fps {

    private GamePanel gamePanel;
    private int frameCount = 0;
    private long lastFpsTime = 0;
    private Text fpsText;

    public Fps(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        setDefaultFpsText();
    }
    private void setDefaultFpsText(){
        fpsText = new Text();
        fpsText.setFont(Font.font(20));
        fpsText.setX(10);
        fpsText.setY(30);
        fpsText.setText("FPS: 0");
        fpsText.setFill(Color.BLACK);
        gamePanel.getChildren().add(fpsText);
    }

    public void update(long now) {
        frameCount++;
        if (now - lastFpsTime >= 1000000000) {
            // Atualize o Text com os FPS
            fpsText.setText("FPS: " + frameCount);
            frameCount = 0;
            lastFpsTime = now;
        }

        fpsText.setX(-gamePanel.getLayoutX() + 10);
        fpsText.setY(-gamePanel.getLayoutY() + 30);
    }
}
