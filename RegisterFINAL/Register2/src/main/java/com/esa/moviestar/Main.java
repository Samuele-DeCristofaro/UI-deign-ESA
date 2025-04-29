package com.esa.moviestar;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;



public class Main extends Application {
    Scene scene;
    @Override
    public void start(Stage primaryStage) throws IOException {
        String css = Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/styles/General.css")).toExternalForm();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home/main.fxml"));
        scene = new Scene (fxmlLoader.load());
        scene.getStylesheets().addAll(css);
        Screen primaryScreen = Screen.getPrimary();
        Rectangle2D screenBounds = primaryScreen.getBounds();
        primaryStage.setMaxWidth(screenBounds.getWidth());
        primaryStage.setMaxHeight(screenBounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Carosello Film");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}