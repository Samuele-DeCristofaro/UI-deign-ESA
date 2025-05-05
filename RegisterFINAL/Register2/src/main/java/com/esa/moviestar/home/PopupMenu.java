package com.esa.moviestar.home;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;

import java.util.Objects;


public class PopupMenu {

    private final Popup popup;
    private final VBox menuBox;

    private final double width = 256;


    public PopupMenu() {
        this.popup = new Popup();
        this.menuBox = new VBox(4);
        menuBox.setPadding(new Insets(8));
        menuBox.setMaxWidth(width);
        menuBox.setMinWidth(width);
        menuBox.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/esa/moviestar/styles/General.css")).toExternalForm());
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        menuBox.getStyleClass().addAll("surface-opaque", "medium-item");
        menuBox.setEffect(shadow);

        // Create container
        StackPane container = new StackPane(menuBox);
        popup.getContent().add(container);
        popup.setAutoHide(true);
    }

    public void addItem(Node n) {
        menuBox.getChildren().add(n);
    }
    public void addSeparator(){
        menuBox.getChildren().add(new VBox(){{ setHeight(4);}});
    }


    public void show(Node anchor) {
        if (anchor == null || anchor.getScene() == null) {
            return;
        }
        double anchorX = anchor.localToScreen(-width+anchor.getLayoutBounds().getWidth(), 0).getX();//menuboxwidth + la larghezza dell'imageview
        double anchorY = anchor.localToScreen(0, 8).getY();
        double posY = anchorY + anchor.getBoundsInLocal().getHeight();
        popup.show(anchor.getScene().getWindow(), anchorX, posY);
    }

}