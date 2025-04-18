package com.example.register2;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;

public class Access {

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
    @FXML
    private ImageView titleImage;
    @FXML
    private VBox loginBox;
    @FXML
    private HBox imageContainer; // Se hai un HBox che contiene l'immagine

    // Valori di riferimento
    private final double REFERENCE_WIDTH = 1720.0; // Usa la larghezza definita nel FXML
    private final double REFERENCE_HEIGHT = 980.0; // Usa l'altezza definita nel FXML
    private final double REFERENCE_LOGIN_WIDTH = 400.0;
    private final double REFERENCE_LOGIN_HEIGHT = 459.0;
    private final double REFERENCE_IMAGE_WIDTH = 700.0;
    private final double REFERENCE_IMAGE_HEIGHT = 194.0;
    private final double MIN_VBOX_VISIBILITY_THRESHOLD = 400.0;
    private final double COMPACT_MODE_THRESHOLD = 500.0;
    private final double IMAGE_VISIBILITY_THRESHOLD = 600.0;

    private UserDatabase userDatabase;

    public void initialize() {
        userDatabase = new UserDatabase();
        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");
        warningText.setText("");
        register.setOnAction(event -> switchToRegistrationPage());
        access.setOnAction(event -> loginUser());
        Node[] formElements = {welcomeText, emailField, passwordField, access, register};
        AnimationUtils.animateSimultaneously(formElements, 1);

        // Configura il layout responsivo
        setupResponsiveLayout();
    }

    private void setupResponsiveLayout() {
        ContenitorePadre.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener((observable, oldValue, newValue) -> adjustLayout(newValue.doubleValue(), newScene.getHeight()));
                newScene.heightProperty().addListener((observable, oldValue, newValue) -> adjustLayout(newScene.getWidth(), newValue.doubleValue()));
                adjustLayout(newScene.getWidth(), newScene.getHeight()); // Adattamento iniziale
            }
        });
    }

    private void adjustLayout(double width, double height) {
        // Fattore di scala basato sulla dimensione MINORE
        double scale = Math.min(width / REFERENCE_WIDTH, height / REFERENCE_HEIGHT);

        // Gestione dell'immagine
        if (titleImage != null) {
            boolean showImage = width > IMAGE_VISIBILITY_THRESHOLD;
            titleImage.setVisible(showImage);
            titleImage.setManaged(showImage);
            if (showImage) {
                titleImage.setFitWidth(REFERENCE_IMAGE_WIDTH * scale);
                titleImage.setFitHeight(REFERENCE_IMAGE_HEIGHT * scale);
            }
        }

        // Gestione del login box
        if (loginBox != null) {
            boolean showLoginBox = width > MIN_VBOX_VISIBILITY_THRESHOLD;
            loginBox.setVisible(showLoginBox);
            loginBox.setManaged(showLoginBox);

            if (showLoginBox) {
                boolean compactMode = width < COMPACT_MODE_THRESHOLD;

                double loginWidth = compactMode ? Math.min(width * 0.9, REFERENCE_LOGIN_WIDTH) : REFERENCE_LOGIN_WIDTH * scale;
                double loginHeight = compactMode ? Math.min(height * 0.9, REFERENCE_LOGIN_HEIGHT) : REFERENCE_LOGIN_HEIGHT * scale;

                loginWidth = Math.max(loginWidth, 280.0);
                loginHeight = Math.max(loginHeight, 300.0);

                loginBox.setPrefWidth(loginWidth);
                loginBox.setPrefHeight(loginHeight);
                loginBox.setMaxWidth(loginWidth);
                loginBox.setMaxHeight(loginHeight);

                // Padding e Spacing dinamici
                double padding = Math.max(10, 20 * scale);
                double spacing = Math.max(5, 10 * scale);
                loginBox.setPadding(new Insets(padding));
                loginBox.setSpacing(spacing);

                // Posizionamento
                StackPane.setAlignment(loginBox, compactMode ? Pos.CENTER : Pos.CENTER_RIGHT);
                StackPane.setMargin(loginBox, compactMode ? new Insets(0) : new Insets(0, Math.max(50, 200 * scale), 0, 0));

                // Dimensioni font dinamiche
                double baseFontSize = 12 * scale;
                welcomeText.setStyle("-fx-font-size: " + (baseFontSize * 2) + "px;");
                warningText.setStyle("-fx-font-size: " + baseFontSize + "px;");
                access.setStyle("-fx-font-size: " + (baseFontSize * 1.25) + "px;");
                register.setStyle("-fx-font-size: " + baseFontSize + "px;");

                // Dimensione campi
                double fieldHeight = Math.max(30, 40 * scale);
                emailField.setPrefHeight(fieldHeight);
                passwordField.setPrefHeight(fieldHeight);

                // Margini dinamici
                double verticalMargin = 10 * scale;
                VBox.setMargin(welcomeText, new Insets(0, 0, verticalMargin * 3, 0));
                VBox.setMargin(emailField, new Insets(0, 0, verticalMargin * 2, 0));
                VBox.setMargin(passwordField, new Insets(0, 0, verticalMargin * 2, 0));
            }
        }
    }

    private void switchToRegistrationPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("registrazione.fxml"));
            Parent registerContent = loader.load();
            registerContent.getStylesheets().add(getClass().getResource("/com/example/register2/register2.css").toExternalForm());
            ContenitorePadre.getChildren().setAll(registerContent); // Usa setAll per una sostituzione più efficiente
        } catch (IOException e) {
            e.printStackTrace();
            warningText.setText("Errore di caricamento: " + e.getMessage());
        }
    }

    private void loginUser() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            warningText.setText("Inserisci email e password");
            AnimationUtils.shake(warningText);
            return;
        }

        try {
            AnimationUtils.pulse(access);

            if (userDatabase.validateUser(email, password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
                Parent homeContent = loader.load();
                homeContent.getStylesheets().add(getClass().getResource("/com/example/register2/home.css").toExternalForm());

                Node currentContent = ContenitorePadre.getChildren().get(0);
                AnimationUtils.fadeOut(currentContent, 500);

                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(e -> {
                    ContenitorePadre.getChildren().setAll(homeContent); // Usa setAll per una sostituzione più efficiente
                    AnimationUtils.fadeIn(homeContent, 500);
                });
                pause.play();
            } else {
                emailField.setText("");
                passwordField.setText("");
                warningText.setText("Account inesistente");
                AnimationUtils.shake(warningText);
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