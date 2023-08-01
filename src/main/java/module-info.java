module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.yaml.snakeyaml;

    opens br.com.andre to javafx.graphics, javafx.fxml, javafx.controls;
}