package com.example.register2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloController {
    @FXML
    private Label welcomeText;
    @FXML
    private Button register;
    @FXML
    private Button access;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label warning1;
    @FXML
    private Label warning2;

    private UserDatabase userDatabase;

    private String email_regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private String password_regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-])[A-Za-z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-]{8,}$";


    @FXML
    public void initialize() {
        userDatabase = new UserDatabase();
        register.setOnAction(event -> saveUser());
    }

    public void saveUser() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!Pattern.matches(email_regex,email)){
            welcomeText.setText("Email non valida");
            return;
        }
        if (!Pattern.matches(password_regex,password)){
            welcomeText.setText("Password non valida");
            return;
        }

        User user = new User(email, password);

        if (!userDatabase.addUser(user)) {
            welcomeText.setText("Utente già esistente");
        } else {
            welcomeText.setText("Utente registrato con successo!");
        }
    }
}

class User {
    private final String email;
    private final String password;

    public User(String email_, String password_) {
        email = email_;
        password = password_;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return email;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

}