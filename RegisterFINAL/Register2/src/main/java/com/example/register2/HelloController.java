package com.example.register2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button access;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private UserDatabase userDatabase;

    @FXML
    public void initialize() {
        userDatabase = new UserDatabase();
        access.setOnAction(event -> saveUser());
    }

    public void saveUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = new User(username, password);

        if (userDatabase.addUser(user)) {
            welcomeText.setText("Utente registrato con successo!");
        } else {
            welcomeText.setText("Username gia' in uso, cambialo");
        }
    }
}

class User {
    private final String name;
    private final String password;

    public User(String username_, String password_) {
        name = username_;
        password = password_;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}