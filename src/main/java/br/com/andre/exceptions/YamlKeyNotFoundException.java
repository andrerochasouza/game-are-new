package br.com.andre.exceptions;

public class YamlKeyNotFoundException extends RuntimeException {
    public YamlKeyNotFoundException(String message) {
        super(message);
    }
}
