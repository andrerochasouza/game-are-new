package br.com.andre.panels;

import br.com.andre.utils.Yml;
import javafx.scene.layout.Pane;

public class GamePanel extends Pane {

    private final int tileSizeOriginal = Yml.get("window.tile-pixels", Integer.class).orElse(16);
    private final int scaleTile = Yml.get("window.scale-tile", Integer.class).orElse(2);
    private final int tileMaxCol = Yml.get("window.tile-max-col", Integer.class).orElse(16);
    private final int tileMaxRow = Yml.get("window.tile-max-row", Integer.class).orElse(12);

    private final int tileSize = this.tileSizeOriginal * this.scaleTile;
    private int screenHeight = this.tileSize * this.tileMaxRow;
    private int screenWidth = this.tileSize * this.tileMaxCol;


    public GamePanel() {
        super();
        this.setPrefSize(this.screenWidth, this.screenHeight);
        this.setStyle("-fx-background-color: #000000;");
    }


}
