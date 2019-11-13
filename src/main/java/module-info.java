module packaging {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens redcreator37 to javafx.fxml;
    exports redcreator37;
}