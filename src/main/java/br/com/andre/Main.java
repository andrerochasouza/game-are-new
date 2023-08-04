package br.com.andre;

import br.com.andre.engine.KeyHandler;
import br.com.andre.entity.Player;
import br.com.andre.panels.Fps;
import br.com.andre.panels.GamePanel;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main extends Application {

    private static final Logger log = LogManager.getLogger(Main.class);
    private Fps fpsPanel;
    private Player player;
    private KeyHandler keyHandler;
    private long lastTime = 0;


    @Override
    public void start(Stage primaryStage) {

        GamePanel gamePanel = new GamePanel();
        Scene scene = new Scene(gamePanel);
        fpsPanel = new Fps(gamePanel);
        keyHandler = new KeyHandler(scene);
        player = new Player(gamePanel, keyHandler, 200, "file:src/main/resources/sprites/Boar");

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastTime >= 1_666_666L) { // 60 fps
                    lastTime = now;
                    gameLoop();
                }

            }
        };

        animationTimer.start();

        primaryStage.setOnCloseRequest(event -> {
            log.info("Closing application...");
            System.exit(0);
        });

    }

    private void gameLoop() {
        update();
        render();
    }

    private void update() {
        fpsPanel.update(System.nanoTime());
        player.update();
    }

    private void render() {
        player.render();
    }

    public static void main(String[] args) {

        log.info("Starting application...");

        try{
            launch(args);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            System.exit(0);
        }

    }
}
