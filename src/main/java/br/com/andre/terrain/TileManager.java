package br.com.andre.terrain;

import br.com.andre.entity.Player;
import br.com.andre.panels.GamePanel;
import br.com.andre.utils.FileUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class TileManager {

    private static final Logger log = LogManager.getLogger(TileManager.class);

    private GamePanel gamePanel;
    private Tile[] tilesImages;
    private Tile[] tilesRender;
    private Player player;
    private Group gruopTiles = new Group();
    private HashMap<String, Boolean> mapIsOn = new HashMap<>();
    private HashMap<String, String> namedPathTiles = new HashMap<>();
    private HashMap<String, String> namedPathMaps = new HashMap<>();

    public TileManager(GamePanel gamePanel, Player player){
        this.gamePanel = gamePanel;
        this.player = player;
    }

    public void render(String nameMap){
        loadMapByName(nameMap);
    }

    public Group getGruopTiles(){
        return this.gruopTiles;
    }

    public void addMap(String name, String pathMap, String pathTiles){
        namedPathMaps.put(name, pathMap);
        namedPathTiles.put(name, pathTiles);
        mapIsOn.put(name, false);
    }

    public Tile getTile(int x, int y) {
        Tile tileCorrect = null;
        double spriteWidth = gamePanel.getTileSize(); // Largura do sprite
        double spriteHeight = gamePanel.getTileSize(); // Altura do sprite

        for (Node node : gruopTiles.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;

                // Verificar se as coordenadas x e y estão dentro dos limites do sprite
                if (x >= imageView.getX() && x < imageView.getX() + spriteWidth &&
                        y >= imageView.getY() && y < imageView.getY() + spriteHeight) {
                    for (Tile tile : tilesRender) {
                        if (Objects.nonNull(tile) && tile.imageView.equals(imageView)) {
                            tileCorrect = tile;
                            break; // Saia do loop interno se encontrar o tile
                        }
                    }
                }
            }
        }

        return tileCorrect;
    }

    private void loadMapByName(String name){

        if(namedPathMaps.containsKey(name)){
            String pathMap = namedPathMaps.get(name);
            String pathTiles = namedPathTiles.get(name);

            if(mapIsOn.get(name).equals(false)){
                log.debug("Loading map: " + name);
                loadTiles(pathTiles);
                tilesRender = new Tile[FileUtils.countCharsFile(pathMap)];
                mapIsOn.forEach((k, v) -> mapIsOn.put(k, false));
                mapIsOn.put(name, true);
            }

            for (String key : mapIsOn.keySet()) {
                if(key.equals(name) && mapIsOn.get(key).equals(true)){
                    loadUpdateMap(pathMap);
                }
            }

        }else{
            log.error("Map not found: " + name);
            throw new RuntimeException("Map not found: " + name);
        }
    }

    private void loadUpdateMap(String pathMap){

        log.debug("=================================================================================");
        log.debug("Removing old map");
        gamePanel.getChildren().remove(this.gruopTiles);

        log.debug("Loading new map");
        long start = System.currentTimeMillis();
        loadMap(pathMap);
        long end = System.currentTimeMillis();
        log.debug("Time to load map: " + (end - start) / 1000.0 + "s");

        log.debug("Adding new map in game panel");
        gamePanel.getChildren().add(this.gruopTiles);

        log.debug("=================================================================================");
    }

    private void loadMap(String pathMap){
        try{
            File mapFile = new File(pathMap);

            if (!mapFile.exists() || !mapFile.isFile()) {
                log.error("Arquivo de mapa não existe ou não é um arquivo.");
                throw new RuntimeException("Arquivo de mapa não existe ou não é um arquivo.");
            }

            log.debug("Carregando mapa: " + pathMap);

            int[][] matrixFileNumbers = FileUtils.fileToMatrix(pathMap);

            int columnNumberFile = player.x / gamePanel.getTileSize();
            int rowNumberFile = player.y / gamePanel.getTileSize();

            log.debug("Column number in File: " + columnNumberFile);
            log.debug("Row number in File: " + rowNumberFile);

            int[][] matrixRenderNumbers = new int[14][18];

            // Percorrer as posições adjacentes e preencher a matrizRenderNumbers
            for (int i = -7; i <= 6; i++) {
                for (int j = -9; j <= 8; j++) {
                    int adjacentRow = rowNumberFile + i + 1;
                    int adjacentColumn = columnNumberFile + j + 1;

                    if (adjacentRow >= 0 && adjacentRow < matrixFileNumbers.length &&
                            adjacentColumn >= 0 && adjacentColumn < matrixFileNumbers[0].length) {
                        matrixRenderNumbers[i + 7][j + 9] = matrixFileNumbers[adjacentRow][adjacentColumn];
                    } else {
                        matrixRenderNumbers[i + 7][j + 9] = -1; // Marcar posições fora dos limites da matriz
                    }
                }
            }

            int tileSize = gamePanel.getTileSize();

            int index = 0;
            for (int i = 0; i < matrixRenderNumbers.length; i++) {
                for (int j = 0; j < matrixRenderNumbers[0].length; j++) {
                    int tileNumber = matrixRenderNumbers[i][j];

                    if (tileNumber != -1) {
                        Tile tile = tilesImages[tileNumber];
                        int x = (j + columnNumberFile - 8) * tileSize;
                        int y = (i + rowNumberFile - 6) * tileSize;

                        if(Objects.isNull(tilesRender[index])){
                            tilesRender[index] = new Tile(tile.imageView.getImage().getUrl().split("file:")[1], tile.isCollidable);
                            tilesRender[index].imageView.setX(x);
                            tilesRender[index].imageView.setY(y);
                            tilesRender[index].imageView.setFitHeight(tileSize);
                            tilesRender[index].imageView.setFitWidth(tileSize);
                            gruopTiles.getChildren().add(tilesRender[index].imageView);
                        } else {
                            tilesRender[index].imageView.setX(x);
                            tilesRender[index].imageView.setY(y);
                            tilesRender[index].imageView.setImage(tile.imageView.getImage());
                            tilesRender[index].isCollidable = tile.isCollidable;
                        }

                        index++;
                    }
                }
            }

        } catch (RuntimeException e){
            log.error("Erro ao carregar mapa", e);
            throw new RuntimeException("Erro ao carregar mapa", e);
        }
    }

    private void loadTiles(String pathTile) {

        try{
            log.debug("Caminho da pasta dos Tiles: {}", pathTile);
            File tilesFolder = new File(pathTile);

            if (!tilesFolder.exists() || !tilesFolder.isDirectory()) {
                log.error("Pasta de tiles não existe ou não é uma pasta.");
                throw new RuntimeException("Pasta de tiles não existe ou não é uma pasta.");
            }

            File[] tileFiles = tilesFolder.listFiles();
            if (tileFiles == null) {
                log.error("Pasta de tiles está vazia.");
                throw new RuntimeException("Pasta de tiles está vazia.");
            }

            int amountTiles = tileFiles.length;
            tilesImages = new Tile[amountTiles];

            File collisionFile = tileFiles[tileFiles.length - 1];
            if (!collisionFile.getName().equals("collision-tiles.txt")) {
                log.error("Arquivo de colisão não encontrado.");
                throw new RuntimeException("Arquivo de colisão não encontrado.");
            } else {
                log.debug("Arquivo de colisão encontrado: {}", collisionFile.getAbsolutePath());
                amountTiles--;
            }

            HashMap<Integer, Boolean> mapCollision = new HashMap<>();
            BufferedReader br = new BufferedReader(new FileReader(collisionFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                mapCollision.put(Integer.parseInt(split[0]), Boolean.parseBoolean(split[1]));
            }

            for (int i = 0; i < amountTiles; i++) {
                File tileFile = tileFiles[i];
                String pathResource = tileFile.getAbsolutePath().replace("\\", "/").split("game-are-new/")[1];
                tilesImages[i] = new Tile(pathResource, mapCollision.get(i));
                log.debug("Tile Carregado: {} - Collision: {}",  pathResource, mapCollision.get(i));
            }

            log.debug("Quantidade de tiles carregados: " + amountTiles);

        } catch (RuntimeException e) {
            log.error("Erro ao carregar tiles", e);
            throw new RuntimeException("Erro ao carregar tiles", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
