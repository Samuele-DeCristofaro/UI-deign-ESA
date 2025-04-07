package com.tonioan.fastmacro;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;



public class Main extends Application {
    @FXML
    VBox root;
    @Override
    public void start(Stage primaryStage) throws IOException {
        String css = Objects.requireNonNull(getClass().getResource("/style/General.css")).toExternalForm();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
        Scene scene = new Scene (fxmlLoader.load());
        scene.getStylesheets().addAll(css);
        primaryStage.setMaxWidth(1920);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Carosello Film");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }
}