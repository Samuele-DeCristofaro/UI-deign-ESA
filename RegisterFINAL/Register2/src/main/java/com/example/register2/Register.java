package com.example.register2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Pattern;

public class Register {
    // FXML injected UI components
    @FXML
    private Label welcomeText;
    @FXML
    private Button register;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label warning1;
    @FXML
    private Label warning2;
    @FXML
    private StackPane ContenitorePadre;
    @FXML
    private Button backToLogin;

    // Database access
    private UserDatabase userDatabase;

    // Regex patterns for email and password validation
    private String email_regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private String password_regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-])[A-Za-z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-]{8,}$";

    /**
     * Initializes the controller. This method is automatically called after the FXML
     * has been loaded and the controller is fully initialized.
     */
    @FXML
    public void initialize() {
        // Initialize the database connection
        userDatabase = new UserDatabase();

        // Set default prompts for email and password fields
        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");

        // Set event handler for the register button
        register.setOnAction(event -> saveUser());

        // Set event handler for the back to login button
        backToLogin.setOnAction(event -> switchToLoginPage());

        // Display password requirements
        warning1.setText("Password con almeno :");
        warning2.setText("8 caratteri");
    }

    /**
     * Update the current page with login content
     */
    private void switchToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent loginContent = loader.load();

            // 👇 AGGIUNGI QUESTO
            loginContent.getStylesheets().add(getClass().getResource("/com/example/register2/access.css").toExternalForm());

            ContenitorePadre.getChildren().clear();
            ContenitorePadre.getChildren().add(loginContent);
        } catch (IOException e) {
            e.printStackTrace();
            welcomeText.setText("Errore di caricamento: " + e.getMessage());
        }
    }


    /**
     * Saves a new user to the database after validating the email and password.
     */
    public void saveUser() {
        // Retrieve email and password from input fields
        String email = emailField.getText();
        String password = passwordField.getText();

        if (!Pattern.matches(email_regex, email) && !Pattern.matches(password_regex, password)) {
            emailField.setPromptText("Email non valida"); // Set error prompt
            emailField.setText("");
            passwordField.setPromptText("Password non valida"); // Set error prompt
            passwordField.setText("");
            return;
        }

        // Validate email using regex
        if (!Pattern.matches(email_regex, email)) {
            emailField.setPromptText("Email non valida"); // Set error prompt
            emailField.setText(""); // Clear the field
            return; // Exit the method if validation fails
        }

        // Validate password using regex
        if (!Pattern.matches(password_regex, password)) {
            passwordField.setPromptText("Password non valida"); // Set error prompt
            passwordField.setText("");  // Clear the field
            return; // Exit the method if validation fails
        }

        // Create a new User object
        User user = new User(email, password);

        // Attempt to add the user to the database
        if (!userDatabase.addUser(user)) {
            // Display error message if user already exists
            welcomeText.setText("Utente già esistente");
        } else {
            // Display success message if user is successfully registered
            welcomeText.setText("Utente registrato con successo!");
        }
    }
}
    /**
     * Represents a user in the system with email and password.
     */
class User {
    // User attributes
    private final String email;
    private final String password;

    /**
     * Constructs a new User object with the specified email and password.
     *
     * @param email_    The user's email address.
     * @param password_ The user's password.
     */
    public User(String email_, String password_) {
        email = email_;
        password = password_;
    }

    /**
     * Retrieves the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retrieves the user's email (used as name in this context).
     *
     * @return The user's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Checks if this User object is equal to another object.
     * Users are considered equal if their email addresses are the same.
     *
     * @param obj The object to compare with.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return email.equals(user.email);
    }

    /**
     * Generates a hash code for this User object based on the email.
     *
     * @return The hash code of the email.
     */
    @Override
    public int hashCode() {
        return email.hashCode();
    }
}
