package br.com.andre;

import br.com.andre.engine.AssetControlObject;
import br.com.andre.engine.Camera;
import br.com.andre.engine.KeyHandler;
import br.com.andre.entity.Player;
import br.com.andre.object.ObjectEnum;
import br.com.andre.panels.Fps;
import br.com.andre.panels.GamePanel;
import br.com.andre.terrain.TileManager;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class App extends Application {

    private static final Logger log = LogManager.getLogger(App.class);
    private Fps fpsPanel;
    private Player player;
    private TileManager[] tileManager;
    private Camera camera;
    private KeyHandler keyHandler;
    private AssetControlObject assetControlObject;
    private long lastTime = 0;


    @Override
    public void start(Stage primaryStage) {

        GamePanel gamePanel = new GamePanel();
        Scene scene = new Scene(gamePanel);
        keyHandler = new KeyHandler(scene);
        player = new Player(gamePanel, keyHandler, 200, "file:src/main/resources/sprites/Boar");
        camera = new Camera(gamePanel);
        fpsPanel = new Fps(gamePanel);

        tileManager = new TileManager[2];
        tileManager[0] = new TileManager(gamePanel, player);
        tileManager[1] = new TileManager(gamePanel, player);

        tileManager[0].addMap("map-1",
                "C:\\github-repositories\\game-are-new\\src\\main\\resources\\sprites\\Maps\\Maps-W16-H12\\tile-map-1.txt",
                "C:\\github-repositories\\game-are-new\\src\\main\\resources\\sprites\\Maps\\Maps-W16-H12\\tile-map-1");

        tileManager[1].addMap("map-1",
                "C:\\github-repositories\\game-are-new\\src\\main\\resources\\sprites\\Maps\\Maps-W16-H12\\construct-map-1.txt",
                "C:\\github-repositories\\game-are-new\\src\\main\\resources\\sprites\\Maps\\Maps-W16-H12\\tile-map-1");

        assetControlObject = new AssetControlObject(gamePanel);
        assetControlObject.addObject(ObjectEnum.KEY, 1, true, 100, 100);
        assetControlObject.addObject(ObjectEnum.DOOR, 1, true, 200, 100);
        assetControlObject.addObject(ObjectEnum.CHEST, 1, false, 300, 100);


        primaryStage.setTitle("Game");
        primaryStage.setResizable(true);
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
        player.update(tileManager);
        camera.update(player);
        assetControlObject.update(ObjectEnum.KEY, ObjectEnum.DOOR);
        fpsPanel.update(System.nanoTime());
    }

    private void render() {
        tileManager[0].render("map-1");
        tileManager[1].render("map-1");
        assetControlObject.render();
        player.render();
    }

    public static void main(String[] args) {

        log.info("Starting application...");

        try{
            launch(args);
        } catch (Exception e){
            log.error("Error starting application: " + e.getMessage());
        } finally {
            System.exit(0);
        }

    }
}
