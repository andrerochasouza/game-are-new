package br.com.andre.database;

import br.com.andre.exceptions.ErrorDatabaseConnectionException;
import br.com.andre.utils.YmlUtils;
import org.sormula.Database;
import org.sormula.SormulaException;


public class DatabaseConnection {

    private static final String PATH = YmlUtils.get("database.path", String.class);

    public static Database getDatabase() {
        try {
            if(inResourcesPath()){
                return new Database("jdbc:sqlite:" + PATH);
            } else {
                return new Database(PATH);
            }
        } catch (RuntimeException | SormulaException e){
            throw new ErrorDatabaseConnectionException(e.getMessage());
        }
    }

    private static boolean inResourcesPath(){
        return DatabaseConnection.PATH.startsWith("src/main/resources");
    }

}
