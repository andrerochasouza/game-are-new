module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.yaml.snakeyaml;
    requires java.sql;
    requires sormula;
    requires org.apache.logging.log4j;

    opens br.com.andre to javafx.graphics, javafx.fxml, javafx.controls;
}