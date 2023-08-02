package br.com.andre.panels;

import br.com.andre.utils.YmlUtils;
import javafx.scene.Scene;
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
        super.setPrefSize(this.screenWidth, this.screenHeight);
        super.setMinSize(this.screenWidth, this.screenHeight);
        super.setMaxSize(this.screenWidth, this.screenHeight);


        logger.info("=================================================================================");
        logger.info("GamePanel initialized");
        logger.info("ScreenHeight: {}", this.screenHeight);
        logger.info("ScreenWidth: {}", this.screenWidth);
        logger.info("TileSize: {}", this.tileSize);
        logger.info("=================================================================================");
    }

    public int getTileSizeOriginal() {
        return tileSizeOriginal;
    }

    public int getScaleTile() {
        return scaleTile;
    }

    public int getTileMaxCol() {
        return tileMaxCol;
    }

    public int getTileMaxRow() {
        return tileMaxRow;
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }
}
