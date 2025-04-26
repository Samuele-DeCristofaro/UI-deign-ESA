module com.example.register2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;


    opens com.example.register2 to javafx.fxml;
    exports com.example.register2;
}