package com.esa.moviestar.Login;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;

public class ResetController {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button resetButton;

    @FXML
    private VBox mainContainer; // Reference to the root VBox

    @FXML
    private Label statusMessage; // Feedback messages

    @FXML
    private StackPane parentContainer; // For page transitions

    public void initialize() {
        // Make sure statusMessage is empty at start
        if (statusMessage != null) {
            statusMessage.setText("");
        }

        // Configure UI components
        newPasswordField.setPromptText("Nuova Password");
        confirmPasswordField.setPromptText("Conferma Nuova Password");

        // Set up action for reset button
        resetButton.setOnAction(event -> validatePasswordReset());

        // Add subtle animation to the elements when loaded
        if (resetButton != null && newPasswordField != null && confirmPasswordField != null) {
            Node[] formElements = {newPasswordField, confirmPasswordField, resetButton};
            AnimationUtils.animateSimultaneously(formElements, 1);
        }
    }

    private void validatePasswordReset() {
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Simple validation only - no actual reset functionality
        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            updateStatus("I campi non possono essere vuoti");
            AnimationUtils.shake(statusMessage);
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            updateStatus("Le password non corrispondono");
            AnimationUtils.shake(statusMessage);
            return;
        }

        // Show success message (no actual implementation)
        updateStatus("Password cambiata con successo");
        AnimationUtils.pulse(resetButton);

        // Transition back to login screen after short delay
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> navigateToLogin());
        pause.play();
    }

    private void updateStatus(String message) {
        if (statusMessage != null) {
            statusMessage.setText(message);
        } else {
            System.out.println("Status message label not found: " + message);
        }
    }

    private void navigateToLogin() {
        try {
            // Load the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esa/moviestar/login.fxml"));
            Parent loginContent = loader.load();
            loginContent.getStylesheets().add(getClass().getResource("/com/esa/moviestar/register2.css").toExternalForm());

            if (parentContainer != null) {
                // Get the current content for animation
                Node currentContent = mainContainer;
                AnimationUtils.fadeOut(currentContent, 500);

                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(e -> {
                    parentContainer.getChildren().setAll(loginContent);
                    AnimationUtils.fadeIn(loginContent, 500);
                });
                pause.play();
            }
        } catch (IOException e) {
            e.printStackTrace();
            updateStatus("Errore durante il caricamento della pagina");
        }
    }
}