package br.com.andre.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

    private static final Logger log = LogManager.getLogger(FileUtils.class);

    public static int countCharsFile(String path){
        int charCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            int charRead;

            while ((charRead = reader.read()) != -1) {
                char character = (char) charRead;

                if (character != '\r' && character != '\n') {
                    charCount++;
                }
            }
        } catch (IOException e) {
            log.error("Erro ao ler arquivo de mapa", e);
            throw new RuntimeException(e);
        }

        return charCount;
    }
}
