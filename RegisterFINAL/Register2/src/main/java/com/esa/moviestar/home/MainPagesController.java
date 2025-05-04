package com.esa.moviestar.home;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;


import java.io.IOException;
import java.util.ResourceBundle;

public class MainPagesController {
    @FXML
    AnchorPane body;
    @FXML
    private StackPane headerContainer;
    private final ResourceBundle resourceBundle =ResourceBundle.getBundle("com.esa.moviestar.images.svg-paths.general-svg");
    private HeaderController headerController;
    private Node homeBody;
    private Node s;
    private HomeController homeBodyController;

    public void  initialize(){
        loadHeader();
        homeBody =loadDynamicBody("home.fxml");
        //s=loadDynamicBody("seconda.fxml");
//        Node home =loadDynamicBody("home.fxml");
//        Node home =loadDynamicBody("home.fxml");
    }

    private void loadHeader(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("header.fxml"),resourceBundle);
            Node headerNode = loader.load();
            headerContainer.getChildren().add(headerNode);
            headerController = loader.getController();
            headerController.homeButton.setOnMouseClicked(e -> {
                if(homeBody == null)
                    return;
                if(body.getChildren().contains(homeBody))
                    return;
                body.getChildren().clear();
                body.getChildren().add(homeBody);

            });
            headerController.filmButton.setOnMouseClicked(e -> {
                if(body.getChildren().contains(s))
                    return;
                if(s == null)
                    s=loadDynamicBody("seconda.fxml");
                body.getChildren().clear();
                body.getChildren().add(s);
            });
            headerController.seriesButton.setOnMouseClicked(e -> {
                if(homeBody == null)
                    return;
                if(body.getChildren().contains(homeBody))
                    return;
                body.getChildren().clear();
                body.getChildren().add(homeBody);
            });
            headerController.searchButton.setOnMouseClicked(e -> {
                if(homeBody == null)
                    return;
                if(body.getChildren().contains(homeBody))
                    return;
                body.getChildren().clear();
                body.getChildren().add(homeBody);
            });
            // homeBodyController.carousel.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Node loadDynamicBody(String bodySource) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(bodySource));
            Node body = loader.load();
            this.body.getChildren().add(body);
            AnchorPane.setBottomAnchor(body,0.0);
            AnchorPane.setTopAnchor(body,0.0);
            AnchorPane.setLeftAnchor(body,0.0);
            AnchorPane.setRightAnchor(body,0.0);
            homeBodyController = loader.getController();//messo solo per provare
            return body;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}