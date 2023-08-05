package br.com.andre.terrain;

import br.com.andre.panels.GamePanel;
import br.com.andre.utils.FileUtils;
import javafx.scene.Group;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ConstructManager {

    private static final Logger log = LogManager.getLogger(TileManager.class);

    private GamePanel gamePanel;
    private Tile[] constructImages;
    private Tile[] constructRender;
    private Group gruopConstruct = new Group();
    private HashMap<String, Boolean> mapIsOn = new HashMap<>();

    private HashMap<String, String> namedPathConstructs = new HashMap<>();
    private HashMap<String, String> namedPathMaps = new HashMap<>();

    public ConstructManager(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }

    public void loadConstructsByName(String nameConstructsMap){
        if(namedPathMaps.containsKey(nameConstructsMap) && mapIsOn.get(nameConstructsMap).equals(false)){
            log.debug("Loading Construct Map: " + nameConstructsMap);
            String pathMap = namedPathMaps.get(nameConstructsMap);
            String pathConstructs = namedPathConstructs.get(nameConstructsMap);
            loadNewConstructMap(pathMap, pathConstructs);
            mapIsOn.forEach((k, v) -> mapIsOn.put(k, false));
            mapIsOn.put(nameConstructsMap, true);
        }
    }

    public void render(String nameConstructsMap){
        loadConstructsByName(nameConstructsMap);
    }

    public Group getGruopConstruct(){
        return this.gruopConstruct;
    }

    public void addConstructMap(String name, String pathConstructMap, String pathConstructMapTiles){
        namedPathMaps.put(name, pathConstructMap);
        namedPathConstructs.put(name, pathConstructMapTiles);
        mapIsOn.put(name, false);
    }

    private void loadNewConstructMap(String pathMap, String pathConstruct){

        log.debug("=================================================================================");
        log.debug("Removing old map construct");
        gamePanel.getChildren().remove(this.gruopConstruct);

        log.debug("Loading news constructs and map");
        loadConstruct(pathConstruct);
        loadMap(pathMap);

        log.debug("Adding new construct map in game panel");
        gamePanel.getChildren().add(this.gruopConstruct);
        this.gruopConstruct.toBack();
        log.debug("=================================================================================");
    }

    private void loadMap(String pathMap){
        try{
            File mapFile = new File(pathMap);

            if (!mapFile.exists() || !mapFile.isFile()) {
                log.error("Arquivo de construct mapa não existe ou não é um arquivo.");
                throw new RuntimeException("Arquivo de construct mapa não existe ou não é um arquivo.");
            }

            int charAmount = FileUtils.countCharsFile(pathMap);
            log.debug("Quantidade de caracteres no arquivo ({}): {}", pathMap, charAmount);

            log.debug("Carregando construct mapa: " + pathMap);
            BufferedReader reader = new BufferedReader(new FileReader(mapFile));
            constructRender = new Tile[charAmount];
            String line;
            int x = 0;
            int z = 0;

            while ((line = reader.readLine()) != null) {
                String[] numberConstructTiles = line.split("");
                for (int y = 0; y < numberConstructTiles.length; y++) {
                    int numberConstructTile = Integer.parseInt(numberConstructTiles[y]);

                    if(numberConstructTile != 0){
                        int index = z + y;
                        Tile tile = constructImages[numberConstructTile];
                        constructRender[index] = new Tile(tile.imageView.getImage().getUrl().split("file:")[1], tile.isCollidable);
                        constructRender[index].imageView.setX(y * gamePanel.getTileSize());
                        constructRender[index].imageView.setY(x * gamePanel.getTileSize());
                        constructRender[index].imageView.setFitHeight(gamePanel.getTileSize());
                        constructRender[index].imageView.setFitWidth(gamePanel.getTileSize());
                        gruopConstruct.getChildren().add(constructRender[index].imageView);
                    }
                }
                x++;
                z += numberConstructTiles.length;
            }

        } catch (IOException e){
            log.error("Erro ao carregar mapa", e);
            throw new RuntimeException("Erro ao carregar mapa", e);
        }
    }


    private void loadConstruct(String pathConstruct) {

        try{
            log.debug("Caminho da pasta dos Constructs Tiles: {}", pathConstruct);
            File constructsTilesFolder = new File(pathConstruct);

            if (!constructsTilesFolder.exists() || !constructsTilesFolder.isDirectory()) {
                log.error("Pasta de Constructs Tiles não existe ou não é uma pasta.");
                throw new RuntimeException("Pasta de Constructs Tiles não existe ou não é uma pasta.");
            }

            File[] constructTileFiles = constructsTilesFolder.listFiles();
            if (constructTileFiles == null) {
                log.error("Pasta de construct tiles está vazia.");
                throw new RuntimeException("Pasta de construct tiles está vazia.");
            }

            int amountConstructTiles = constructTileFiles.length;
            constructImages = new Tile[amountConstructTiles];

            for (int i = 0; i < amountConstructTiles; i++) {
                File constructsTileFile = constructTileFiles[i];
                String pathResource = constructsTileFile.getAbsolutePath().replace("\\", "/").split("game-are-new/")[1];
                log.debug("Carregando construct tile: " + pathResource);
                constructImages[i] = new Tile(pathResource, true);
            }

            log.debug("Quantidade de construct tiles carregados: " + amountConstructTiles);

        } catch (RuntimeException e) {
            log.error("Erro ao carregar construct tiles", e);
            throw new RuntimeException("Erro ao carregar construct tiles", e);
        }
    }

}
