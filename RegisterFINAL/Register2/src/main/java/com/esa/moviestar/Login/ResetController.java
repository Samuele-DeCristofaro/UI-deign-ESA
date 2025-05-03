package com.esa.moviestar.Login;

import com.esa.moviestar.Database.AccountDao;
import com.esa.moviestar.Database.DataBaseManager;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class ResetController {

    @FXML
    private TextField codeField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button resetButton;

    @FXML
    private VBox mainContainer;

    @FXML
    private Label statusMessage;

    @FXML
    private StackPane parentContainer;

    // Attributi per il reset della password
    private String userEmail;
    private String verificationCode;

    public void initialize() {
        // Make sure statusMessage is empty at start
        if (statusMessage != null) {
            statusMessage.setText("");
        }

        // Configure UI components
        if (codeField != null) {
            codeField.setPromptText("Codice di verifica");
        }

        if (newPasswordField != null) {
            newPasswordField.setPromptText("Nuova Password");
        }

        if (confirmPasswordField != null) {
            confirmPasswordField.setPromptText("Conferma Nuova Password");
        }

        // Add subtle animation to the elements when loaded
        if (resetButton != null && newPasswordField != null &&
                confirmPasswordField != null && codeField != null) {
            Node[] formElements = {codeField, newPasswordField, confirmPasswordField, resetButton};
            AnimationUtils.animateSimultaneously(formElements, 1);
        }
    }

    // Setters per i valori necessari al reset
    public void setUserEmail(String email) {
        this.userEmail = email;
    }

    public void setVerificationCode(String code) {
        this.verificationCode = code;
    }

    // Metodo per configurare il pulsante di reset con i relativi dati
    public void setupResetButton() {
        if (resetButton != null) {
            //resetButton.setOnAction(event -> validatePasswordReset());
        }
    }

    private void validatePasswordReset() {
        String inputCode = codeField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Controllo se i campi sono vuoti
        if (inputCode.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            updateStatus("I campi non possono essere vuoti");
            AnimationUtils.shake(statusMessage);
            return;
        }

        // Controllo se il codice di verifica corrisponde
        if (!inputCode.equals(verificationCode)) {
            updateStatus("Codice di verifica errato");
            AnimationUtils.shake(statusMessage);
            return;
        }

        // Controllo se le password corrispondono
        if (!newPassword.equals(confirmPassword)) {
            updateStatus("Le password non corrispondono");
            AnimationUtils.shake(statusMessage);
            return;
        }

        // Controllo la validità della password usando il pattern regex dal Register
        Register tempRegister = new Register();
        if (!Pattern.matches(tempRegister.get_regex(), newPassword)) {
            updateStatus("La password non rispetta i requisiti di sicurezza");
            AnimationUtils.shake(statusMessage);
            return;
        }
    }

    /*    try {
            // Aggiorna la password nel database
            cambiaPassword(userEmail, newPassword);

            // Mostra messaggio di successo
            updateStatus("Password cambiata con successo");
            AnimationUtils.pulse(resetButton);

            // Torna alla schermata di login dopo un breve ritardo
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> navigateToLogin());
            pause.play();
        } catch (SQLException e) {
            e.printStackTrace();
            updateStatus("Errore durante il reset della password: " + e.getMessage());
        }
    }

    /*private void cambiaPassword(String email, String newPassword) throws SQLException {
        Connection connection = DataBaseManager.getConnection("jdbc:sqlite:C:\\Users\\ssamu\\IdeaProjects\\UI-deign-ESA\\RegisterFINAL\\Register2\\src\\main\\resources\\com\\esa\\moviestar\\DatabaseProjectUID.db");
        AccountDao dao = new AccountDao(connection);
        dao.updatePassword(email, newPassword);
    }*/

    private void updateStatus(String message) {
        if (statusMessage != null) {
            statusMessage.setText(message);
        } else {
            System.out.println("Status message label not found: " + message);
        }
    }

    private void navigateToLogin() {
        try {
            // Carica la schermata di login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/esa/moviestar/login.fxml"));
            Parent loginContent = loader.load();
            loginContent.getStylesheets().add(getClass().getResource("/com/esa/moviestar/access.css").toExternalForm());

            if (parentContainer != null) {
                // Ottieni il contenuto attuale per l'animazione
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