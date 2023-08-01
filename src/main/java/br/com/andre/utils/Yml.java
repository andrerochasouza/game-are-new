package br.com.andre.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Optional;
import org.yaml.snakeyaml.Yaml;

public class Yml {

    private static final String ARQUIVO_YAML = "src/main/resources/application.yaml";

    public static <T> Optional<T> get(String chave, Class<T> tipoRetorno) {
        try {
            FileInputStream fileInputStream = new FileInputStream(ARQUIVO_YAML);
            Yaml yaml = new Yaml();
            Map<String, Object> yamlMap = yaml.load(fileInputStream);
            return buscarValorRecursivamente(yamlMap, chave, tipoRetorno);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private static <T> Optional<T> buscarValorRecursivamente(Map<String, Object> map, String chave, Class<T> tipoRetorno) {
        for (String key : map.keySet()) {
            if (key.equals(chave)) {
                Object valor = map.get(key);
                if (tipoRetorno.isInstance(valor)) {
                    return Optional.of(tipoRetorno.cast(valor));
                }
            } else if (map.get(key) instanceof Map) {
                Optional<T> valor = buscarValorRecursivamente((Map<String, Object>) map.get(key), chave, tipoRetorno);
                if (valor.isPresent()) {
                    return valor;
                }
            }
        }
        return Optional.empty();
    }
}
