package br.com.andre.panels;

import br.com.andre.utils.YmlUtils;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class GamePanel extends Pane {

    private static final Logger logger = LogManager.getLogger(GamePanel.class);

    private final int tileSizeOriginal = YmlUtils.get("window.tile-pixels", Integer.class);
    private final int scaleTile = YmlUtils.get("window.scale-tile", Integer.class);
    private final int tileMaxCol = YmlUtils.get("window.tile-max-col", Integer.class);
    private final int tileMaxRow = YmlUtils.get("window.tile-max-row", Integer.class);

    private final int tileSize = this.tileSizeOriginal * this.scaleTile;
    private final int screenHeight = this.tileSize * this.tileMaxRow;
    private final int screenWidth = this.tileSize * this.tileMaxCol;


    public GamePanel() {
        super();
        this.setPrefSize(this.screenWidth, this.screenHeight);
        this.setStyle("-fx-background-color: #000000;");
        logger.info("=================================================================================");
        logger.info("GamePanel initialized");
        logger.info("ScreenHeight: {}", this.screenHeight);
        logger.info("ScreenWidth: {}", this.screenWidth);
        logger.info("TileSize: {}", this.tileSize);
        logger.info("=================================================================================");

    }


}
