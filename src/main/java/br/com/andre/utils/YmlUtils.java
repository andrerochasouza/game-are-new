package br.com.andre.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import br.com.andre.exceptions.YamlKeyNotFoundException;
import br.com.andre.exceptions.YamlValueTypeMismatchException;
import org.yaml.snakeyaml.Yaml;

public class YmlUtils {

    private static final String ARQUIVO_YAML = "src/main/resources/application.yaml";

    public static <T> T get(String chave, Class<T> tipoRetorno) throws YamlKeyNotFoundException, YamlValueTypeMismatchException {
        try (FileInputStream fileInputStream = new FileInputStream(ARQUIVO_YAML)) {
            Yaml yaml = new Yaml();
            Map<String, Object> yamlMap = yaml.load(fileInputStream);
            return buscarValorRecursivamente(yamlMap, chave, tipoRetorno)
                    .orElseThrow(() -> new YamlKeyNotFoundException("Chave não encontrada: " + chave));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Optional<T> buscarValorRecursivamente(Map<String, Object> map, String chave, Class<T> tipoRetorno) {
        for (String key : map.keySet()) {
            if (key.equals(chave)) {
                Object valor = map.get(key);
                if (tipoRetorno.isInstance(valor)) {
                    return Optional.of(tipoRetorno.cast(valor));
                } else {
                    throw new YamlValueTypeMismatchException("Tipo de valor incompatível para chave: " + chave);
                }
            } else if (map.get(key) instanceof Map) {
                String[] partesChave = chave.split("\\.");
                if (partesChave.length > 1 && partesChave[0].equals(key)) {
                    Optional<T> valor = buscarValorRecursivamente((Map<String, Object>) map.get(key), partesChave[1], tipoRetorno);
                    if (valor.isPresent()) {
                        return valor;
                    }
                }
            }
        }
        return Optional.empty();
    }
}
