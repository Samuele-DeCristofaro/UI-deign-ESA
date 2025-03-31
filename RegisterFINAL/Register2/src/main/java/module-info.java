module com.example.register2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.register2 to javafx.fxml;
    exports com.example.register2;
}