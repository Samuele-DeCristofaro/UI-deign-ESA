package com.example.register2;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
    private Label warningSpecial;
    @FXML
    private StackPane ContenitorePadre;
    @FXML
    private Button backToLogin;
    @FXML
    private VBox registerBox;
    @FXML
    private ImageView titleImage;

    // Database access
    private UserDatabase userDatabase;

    // Regex patterns for email and password validation
    private String email_regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private String password_regex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-])[A-Za-z\\d!@#$%^&*()_+{}\\[\\]:;<>,.?~\\-]{8,}$";

    // Valori di riferimento per il layout responsivo
    private final double REFERENCE_WIDTH = 1720.0;
    private final double REFERENCE_HEIGHT = 980.0;
    private final double REFERENCE_REGISTER_WIDTH = 433.0;
    private final double REFERENCE_REGISTER_HEIGHT = 459.0;
    private final double REFERENCE_IMAGE_WIDTH = 700.0;
    private final double REFERENCE_IMAGE_HEIGHT = 194.0;
    private final double MIN_SCREEN_WIDTH = 600.0;
    private final double MIN_SCREEN_HEIGHT = 700.0;

    /**
     * Initializes the controller. This method is automatically called after the FXML
     * has been loaded and the controller is fully initialized.
     */
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
        warningSpecial.setText("1 carattere speciale o numero");

        // Apply animations sequentially
        Node[] formElements = {welcomeText, emailField, passwordField, warning1, warningSpecial, warning2, register, backToLogin};
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
        // Calcola il fattore di scala basato sulla dimensione della finestra
        double scaleWidth = Math.max(width / REFERENCE_WIDTH, MIN_SCREEN_WIDTH / REFERENCE_WIDTH);
        double scaleHeight = Math.max(height / REFERENCE_HEIGHT, MIN_SCREEN_HEIGHT / REFERENCE_HEIGHT);
        double scale = Math.min(scaleWidth, scaleHeight);

        // Ridimensiona l'immagine del titolo
        titleImage.setFitWidth(REFERENCE_IMAGE_WIDTH * scale);
        titleImage.setFitHeight(REFERENCE_IMAGE_HEIGHT * scale);

        // Ridimensiona la VBox di registrazione
        double registerBoxWidth = REFERENCE_REGISTER_WIDTH * scale;
        double registerBoxHeight = REFERENCE_REGISTER_HEIGHT * scale;

        registerBox.setPrefWidth(registerBoxWidth);
        registerBox.setPrefHeight(registerBoxHeight);
        registerBox.setMaxWidth(registerBoxWidth);
        registerBox.setMaxHeight(registerBoxHeight);

        // Aggiorna padding e spaziatura
        double padding = 20 * scale;
        double spacing = 10 * scale;
        registerBox.setPadding(new Insets(padding));
        registerBox.setSpacing(spacing);

        // Aggiorna la dimensione dei font
        double baseFontSize = 12 * scale;
        welcomeText.setStyle("-fx-font-size: " + (baseFontSize * 2) + "px;");
        warning1.setStyle("-fx-font-size: " + baseFontSize + "px;");
        warning2.setStyle("-fx-font-size: " + baseFontSize + "px;");
        warningSpecial.setStyle("-fx-font-size: " + baseFontSize + "px;");
        register.setStyle("-fx-font-size: " + (baseFontSize * 1.25) + "px;");
        backToLogin.setStyle("-fx-font-size: " + baseFontSize + "px;");

        // Aggiorna la dimensione dei campi di input
        double fieldHeight = 40 * scale;
        emailField.setPrefHeight(fieldHeight);
        passwordField.setPrefHeight(fieldHeight);
        register.setPrefHeight(fieldHeight);

        // Aggiorna i margini
        double marginSmall = 10 * scale;
        double marginMedium = 20 * scale;
        double marginLarge = 30 * scale;

        VBox.setMargin(welcomeText, new Insets(0, 0, marginMedium, 0));
        VBox.setMargin(emailField, new Insets(0, 0, marginMedium, 0));
        VBox.setMargin(passwordField, new Insets(0, 0, marginMedium, 0));
        VBox.setMargin(warning2, new Insets(0, 0, marginMedium, 0));
        VBox.setMargin(backToLogin, new Insets(marginSmall, 0, 0, 0));
        VBox.setMargin(titleImage, new Insets(marginLarge, 0, 0, 0));
        VBox.setMargin(registerBox, new Insets(marginLarge, 0, 0, 0));
    }

    /**
     * Update the current page with login content
     */
    private void switchToLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Parent loginContent = loader.load();
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
            AnimationUtils.shake(emailField);
            AnimationUtils.shake(passwordField);
            emailField.setPromptText("Email non valida");
            emailField.setText("");
            passwordField.setPromptText("Password non valida");
            passwordField.setText("");
            return;
        }

        // Validate email using regex
        if (!Pattern.matches(email_regex, email)) {
            AnimationUtils.shake(emailField);
            emailField.setPromptText("Email non valida");
            emailField.setText("");
            return;
        }

        // Validate password using regex
        if (!Pattern.matches(password_regex, password)) {
            AnimationUtils.shake(passwordField);
            passwordField.setPromptText("Password non valida");
            passwordField.setText("");
            return;
        }

        // Show pulse animation on register button to indicate processing
        AnimationUtils.pulse(register);

        // Create a new User object
        User user = new User(email, password);

        // Attempt to add the user to the database
        if (!userDatabase.addUser(user)) {
            // Display error message if user already exists
            welcomeText.setText("Utente già esistente");
            AnimationUtils.shake(welcomeText);
        } else {
            // Display success message with animation if user is successfully registered
            welcomeText.setText("Utente registrato con successo!");
            AnimationUtils.pulse(welcomeText);
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
