package br.com.andre;

import br.com.andre.panels.GamePanel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Main extends Application {
    private static final double VELOCIDADE_MOVIMENTO = 5.0;

    private Circle jogador;

    @Override
    public void start(Stage stage) {
        GamePanel gamePanel = new GamePanel();
        Scene cena = new Scene(gamePanel);

        jogador = new Circle(30, Color.RED);
        jogador.setCenterX(gamePanel.getMaxHeight() / 2);
        jogador.setCenterY(gamePanel.getMaxWidth() / 2);
        gamePanel.getChildren().add(jogador);

        cena.setOnKeyPressed(e -> moverJogador(e.getCode()));

        stage.setTitle("Exemplo de Jogo com JavaFX");
        stage.setScene(cena);
        stage.show();
    }

    private void moverJogador(KeyCode keyCode) {

        switch (keyCode) {
            case UP:
                jogador.setCenterY(jogador.getCenterY() - VELOCIDADE_MOVIMENTO);
                break;
            case DOWN:
                jogador.setCenterY(jogador.getCenterY() + VELOCIDADE_MOVIMENTO);
                break;
            case LEFT:
                jogador.setCenterX(jogador.getCenterX() - VELOCIDADE_MOVIMENTO);
                break;
            case RIGHT:
                jogador.setCenterX(jogador.getCenterX() + VELOCIDADE_MOVIMENTO);
                break;
        }
    }

    public static void main(String[] args) {

        // Init the JavaFX application
        launch(args);

    }
}
