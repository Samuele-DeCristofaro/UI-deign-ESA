module com.example.register2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.mail;
    requires java.sql;


    opens com.example.register2 to javafx.fxml;
    exports com.example.register2;
    exports com.example.register2.DAO;
    opens com.example.register2.DAO to javafx.fxml;
    exports com.example.register2.Profile;
    opens com.example.register2.Profile to javafx.fxml;
}