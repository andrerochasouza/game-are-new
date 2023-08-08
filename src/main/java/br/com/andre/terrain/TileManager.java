package br.com.andre.terrain;

import br.com.andre.panels.GamePanel;
import br.com.andre.utils.FileUtils;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Timer;

public class TileManager {

    private static final Logger log = LogManager.getLogger(TileManager.class);

    private GamePanel gamePanel;
    private Tile[] tilesImages;
    private Tile[] tilesRender;
    private Group gruopTiles = new Group();
    private HashMap<String, Boolean> mapIsOn = new HashMap<>();
    private HashMap<String, String> namedPathTiles = new HashMap<>();
    private HashMap<String, String> namedPathMaps = new HashMap<>();

    public TileManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
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
                        if (tile.imageView.equals(imageView)) {
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
        if(namedPathMaps.containsKey(name) && mapIsOn.get(name).equals(false)){
            log.debug("Loading map: " + name);
            String pathMap = namedPathMaps.get(name);
            String pathTiles = namedPathTiles.get(name);
            loadNewMap(pathMap, pathTiles);
            mapIsOn.forEach((k, v) -> mapIsOn.put(k, false));
            mapIsOn.put(name, true);
        }
    }

    private void loadNewMap(String pathMap, String pathTile){

        log.debug("=================================================================================");
        log.debug("Removing old map");
        gamePanel.getChildren().remove(this.gruopTiles);

        log.debug("Loading news tiles and map");
        long start = System.currentTimeMillis();
        loadTiles(pathTile);
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
            tilesRender = new Tile[FileUtils.countCharsFile(pathMap)];

            int[][] matrixFileNumbers = FileUtils.fileToMatrix(pathMap);

            int index = 0;
            for (int x = 0; x < matrixFileNumbers.length; x++) {
                for (int y = 0; y < matrixFileNumbers[0].length; y++) {
                    if(matrixFileNumbers[x][y] != -1){
                        Tile tile = tilesImages[matrixFileNumbers[x][y]];
                        tilesRender[index] = new Tile(tile.imageView.getImage().getUrl().split("file:")[1], tile.isCollidable);
                        tilesRender[index].imageView.setX(y * gamePanel.getTileSize());
                        tilesRender[index].imageView.setY(x * gamePanel.getTileSize());
                        tilesRender[index].imageView.setFitHeight(gamePanel.getTileSize());
                        tilesRender[index].imageView.setFitWidth(gamePanel.getTileSize());
                        gruopTiles.getChildren().add(tilesRender[index].imageView);
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
