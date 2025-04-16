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
import javafx.scene.Node;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Access {
    // FXML injected UI components
    @FXML
    private Label welcomeText;
    @FXML
    private Label warningText;
    @FXML
    private Button register;
    @FXML
    private Button access;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private StackPane ContenitorePadre;

    // Database access
    private UserDatabase userDatabase;

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

        // Clear the warning text initially
        warningText.setText("");

        // Set event handler for the register button to navigate to registration screen
        register.setOnAction(event -> switchToRegistrationPage());

        // Set event handler for the access button
        access.setOnAction(event -> loginUser());
        Node[] formElements = {welcomeText, emailField, passwordField, access, register};
        AnimationUtils.animateSimultaneously(formElements, 1); // Use slideInFromRight with 100ms delay between each
    }

    /**
     * Updates the current page with registration content
     */
    private void switchToRegistrationPage() {
        try {
            // Load the registration FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registrazione.fxml"));
            Parent registerContent = loader.load();
            registerContent.getStylesheets().add(getClass().getResource("/com/example/register2/register2.css").toExternalForm());


            // Clear the current content of the StackPane
            ContenitorePadre.getChildren().clear();

            // Add the new content to the StackPane
            ContenitorePadre.getChildren().add(registerContent);
        } catch (IOException e) {
            e.printStackTrace();
            warningText.setText("Errore di caricamento: " + e.getMessage());
        }
    }

    /**
     * Attempts to log in the user with the provided credentials
     */
    private void loginUser() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            warningText.setText("Inserisci email e password");
            AnimationUtils.shake(warningText); // Add shake animation
            return;
        }

        try {
            // Show pulse animation on access button to indicate processing
            AnimationUtils.pulse(access);

            // Verifica le credenziali
            if (userDatabase.validateUser(email, password)) {
                // Carica la nuova schermata (es. home.fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                Parent homeContent = loader.load();

                // Applica il CSS se necessario
                homeContent.getStylesheets().add(getClass().getResource("/com/example/register2/home.css").toExternalForm());

                // Fade out current content
                Node currentContent = ContenitorePadre.getChildren().get(0);
                AnimationUtils.fadeOut(currentContent, 500);

                // Wait for fade out to complete before replacing content
                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(e -> {
                    // Sostituisci il contenuto del contenitore padre
                    ContenitorePadre.getChildren().clear();
                    ContenitorePadre.getChildren().add(homeContent);

                    // Fade in new content
                    AnimationUtils.fadeIn(homeContent, 500);
                });
                pause.play();
            } else {
                emailField.setText("");
                passwordField.setText("");
                warningText.setText("Account inesistente");
                AnimationUtils.shake(warningText); // Add shake animation
            }
        } catch (IOException e) {
            e.printStackTrace();
            warningText.setText("Errore di caricamento: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            warningText.setText("Errore durante l'accesso");
        }
    }
}