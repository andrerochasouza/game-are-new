package br.com.andre;

import br.com.andre.panels.GamePanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static javafx.application.Application.launch;

public class Main extends Application {

    private static final Logger log = LogManager.getLogger(Main.class);
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {

        GamePanel gamePanel = new GamePanel();
        this.scene = new Scene(gamePanel);

        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        ImageView image = new ImageView("file:src/main/resources/sprites/MiniWorldSprites/Animals/Boar.png");
        image.setX(0);
        image.setY(0);
        image.setFitWidth(gamePanel.getTileSize());
        image.setFitHeight(gamePanel.getTileSize());

        gamePanel.getChildren().add(image);

        primaryStage.setOnCloseRequest(event -> {
            log.info("Closing application...");
            System.exit(0);
        });


//        AnimationTimer animationTimer = new AnimationTimer() {
//            long lastUpdate = 0;
//
//            @Override
//            public void handle(long now) {
//                // Atualize a cena apenas a cada 1 segundo (por exemplo)
//                if (now - lastUpdate >= 1_000_000_000) {
//                    // Chame o método de atualização da cena aqui
//                    updateSceneWithNewData();
//                    lastUpdate = now;
//                }
//            }
//        };
//
//        animationTimer.start();

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
