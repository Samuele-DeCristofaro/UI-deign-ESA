package com.esa.moviestar.home;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;


public class HeaderController {
    private PopupMenu popupMenu;

    @FXML
    private StackPane rootHeader;
    @FXML
    private HBox titleImageContainer;

    @FXML
    StackPane homeButton;

    @FXML
    HBox filmButton;

    @FXML
    HBox seriesButton;

    @FXML
    HBox searchButton;

    @FXML
    private TextField tbxSearch;

    private Node currentActive;
    @FXML
    private ImageView profileImage;

    @FXML
    public void initialize() {

        activeButton(homeButton);
        profileImage.setOnMouseClicked(e -> popupMenu.show(profileImage));
        // da spostare
        homeButton.setOnMouseReleased(e -> activeButton(homeButton));
        filmButton.setOnMouseReleased(e -> activeButton(filmButton));
        seriesButton.setOnMouseReleased(e -> activeButton(seriesButton));
        searchButton.setOnMouseReleased(e -> activeSearchButton(searchButton));
        tbxSearch.setOnMouseReleased(e -> activeSearchButton(searchButton));
        tbxSearch.setOnMouseExited(e-> tbxSearch.getParent().requestFocus());
//        rootHeader.widthProperty().addListener((observable, oldValue, newValue) -> {
//            // Show the image
//            titleImageContainer.setVisible(!(newValue.doubleValue() < 1080));
//        });
        List<Node> l = new ArrayList<>();
        Region n = new Region();
        l.add(n);
        setupPopupMenu(l);
    }

    private void activeSearchButton(Node searchButton) {
        if(currentActive == searchButton)
            return;
        if (currentActive != null) {
            currentActive.getStyleClass().remove("nav-button-active");
        }
        searchButton.getStyleClass().add("nav-search.svg-button-active");
        currentActive = searchButton;
    }

    public void activeButton(Node button) {
        if(currentActive == button)
            return;
        // Remove active class from previous button
        if (currentActive != null) {
            if (currentActive == searchButton)
                currentActive.getStyleClass().remove("nav-search.svg-button-active");
            currentActive.getStyleClass().remove("nav-button-active");
        }
        // Add active class to new button
        button.getStyleClass().add("nav-button-active");
        currentActive = button;
    }
//    public void setContent(list<>){
//        setupPopupMenu();
//    }

    private void setupPopupMenu(List<Node> content) {
        // Create the popup menu - no stage needed
        popupMenu = new PopupMenu();

        // Set callback to handle menu item selections
        popupMenu.setMenuCallback(this::handleMenuSelection);

        // Add menu items
        popupMenu.addHeaderItem("My Account", "settings");

        for(Node i : content){
            popupMenu.addProfileItem("Giacomino", new Image(getClass().getResource("/com/esa/moviestar/images/untitled.png").toExternalForm()), "profile_giacomino");
        }
        // Add a button with action
        popupMenu.addFooterItem("Change Email", this::handleEmailChange);
    }

    private void handleMenuSelection(String itemId) {
        // Handle menu selections based on ID
        System.out.println("selected profile n:"+ itemId);
    }

    private void handleEmailChange() {
        System.out.println("Change email button clicked");
        // Handle email change logic here
    }
}