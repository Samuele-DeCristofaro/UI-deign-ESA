package com.tonioan.fastmacro;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Objects;

public class TitleBox extends HBox {

    private Label titleLabel;
    private Line separator;
    private Button arrowButton;

    public TitleBox(String title,boolean isLineVisible,boolean isButtonVisible,int height,int padding,String backgroundColor,String textColor) {

        // Configure the container
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPadding(new Insets(0, padding, 0, padding));
        this.setPrefHeight(height);
        this.setStyle("-fx-background-color:"+backgroundColor+";");
        // Create and configure the label
        titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill:"+textColor+";");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 18));
        titleLabel.setPadding(new Insets(0, 15, 0, 0));

        // Create the separator line
        separator = new Line();
        separator.setStroke(Color.web(textColor));
        separator.setStrokeWidth(1);

        // Create a StackPane to hold the line
        StackPane lineContainer = new StackPane(separator);
        lineContainer.setAlignment(Pos.CENTER);
        HBox.setHgrow(lineContainer, Priority.ALWAYS);

        // Create the button
        arrowButton = new Button();
        arrowButton.setStyle("-fx-background-color: transparent; -fx-text-fill: "+textColor+";");
        arrowButton.setPrefWidth(height);
        arrowButton.setPrefHeight(height);
        try {
            String iconPath = "/images/arrow-right.png";
            ImageView icon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(iconPath)).openStream()));
            icon.setFitWidth((double) height /2);
            icon.setFitHeight((double) height /2);
            arrowButton.setGraphic(icon);
        } catch (Exception e) {
            // Fallback to text if icon cannot be loaded
            arrowButton.setText("▶");
            System.out.println("Impossibile caricare l'icona: " + e.getMessage());
        }
        // Add all components to the container
        this.getChildren().addAll(titleLabel, lineContainer, arrowButton);

        // Bind the line width to the container width
        separator.startXProperty().set(0);
        separator.endXProperty().bind(lineContainer.widthProperty().subtract(20)); // Subtract some padding
    }
    
    public Label getTitleLabel() {
        return titleLabel;
    }

    public Line getSeparator() {
        return separator;
    }

    public Button getArrowButton() {
        return arrowButton;
    }


//    // Method to change the title
//    public void setTitle(String title) {
//        titleLabel.setText(title);
//    }
//
//    // Method to change the button text/icon
//    public void setButtonText(String text) {
//        arrowButton.setText(text);
//    }
}