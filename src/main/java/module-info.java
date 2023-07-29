module app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens br.com.andre to javafx.graphics, javafx.fxml, javafx.controls;
}