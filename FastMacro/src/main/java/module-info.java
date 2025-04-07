module com.tonioan.fastmacro {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.tonioan.fastmacro to javafx.fxml;
    exports com.tonioan.fastmacro;
}