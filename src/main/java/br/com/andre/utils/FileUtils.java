package br.com.andre.utils;

import br.com.andre.entity.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileUtils {

    private static final Logger log = LogManager.getLogger(FileUtils.class);


    public static int countCharsFile(String path){
        int charCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int charRead;

            while ((charRead = reader.read()) != -1) {
                if(charRead != '\n' && charRead != '\r' && charRead != '\t' && charRead != ' '){
                    charCount++;
                }
            }
        } catch (IOException e) {
            log.error("Erro ao ler arquivo de mapa", e);
            throw new RuntimeException(e);
        }

        return charCount;
    }

    public static int[][] fileToMatrix(String pathFile) {
        try(BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
            ArrayList<ArrayList<Integer>> matrixList = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] numberStrings = line.trim().split(" ");
                ArrayList<Integer> row = new ArrayList<>();
                for (String numberString : numberStrings) {
                    if(numberString.equals("@")){
                        row.add(-1);
                    } else {
                        row.add(Integer.parseInt(numberString));
                    }
                }
                matrixList.add(row);
            }

            int numRows = matrixList.size();
            int numCols = matrixList.get(0).size();
            int[][] matrix = new int[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCols; j++) {
                    matrix[i][j] = matrixList.get(i).get(j);
                }
            }

            reader.close();
            return matrix;

        } catch (IOException e) {
            log.error("Erro ao ler o arquivo", e);
            throw new RuntimeException("Erro ao ler o arquivo", e);
        }
    }
}
