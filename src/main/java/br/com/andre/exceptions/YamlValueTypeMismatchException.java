package br.com.andre.exceptions;

public class YamlValueTypeMismatchException extends RuntimeException {
    public YamlValueTypeMismatchException(String message) {
        super(message);
    }
}