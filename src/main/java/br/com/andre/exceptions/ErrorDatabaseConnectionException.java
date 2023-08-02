package br.com.andre.exceptions;

import javafx.scene.control.Alert;

public class ErrorDatabaseConnectionException extends RuntimeException{

    public ErrorDatabaseConnectionException(String message) {
        super(message);
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro no Banco de Dados");
        alert.setHeaderText(null);
        alert.setContentText("ERROR: " + message);
        alert.showAndWait();
    }
}
