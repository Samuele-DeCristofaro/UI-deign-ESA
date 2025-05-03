package com.esa.moviestar.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.Popup;

public class PopupMenu {

    private final Popup popup;
    private final VBox menuBox;
    private MenuCallback callback;

    // Material Colors
    private final Color surfaceColor = Color.rgb(15, 15, 15);
    private final Color onSurfaceColor = Color.rgb(232, 232, 232);
    private final Color primaryColor = Color.rgb(232, 232, 232);
    private final Color outlineColor = Color.rgb(31, 31, 31);
    private final double width = 256;
    public interface MenuCallback {
        void onMenuItemSelected(String item);
    }

    public PopupMenu() {
        this.popup = new Popup();

        // Create menu content container
        this.menuBox = new VBox(4);
        menuBox.setPadding(new Insets(8, 0, 8, 0));
        menuBox.setMaxWidth(width);
        menuBox.setMinWidth(width);

        // Material Design elevation with shadow
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));

        menuBox.setBackground(new Background(new BackgroundFill(
                surfaceColor, new CornerRadii(16), Insets.EMPTY)));
        menuBox.setBorder(new Border(new BorderStroke(
                outlineColor, BorderStrokeStyle.SOLID, new CornerRadii(16), new BorderWidths(1))));
        menuBox.setEffect(shadow);

        // Create container
        StackPane container = new StackPane(menuBox);

        popup.getContent().add(container);
        popup.setAutoHide(true);
    }

    public void setMenuCallback(MenuCallback callback) {
        this.callback = callback;
    }

    public void addHeaderItem(String text, String id) {
        HBox item = new HBox(8);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(8, 8, 8, 16));
        item.setId(id);

        // Material Design avatar
        SVGPath profileIcon = new SVGPath();
        profileIcon.setContent("M26 31 6 31V23C6 20 9 20 9 20H23C23 20 26 20 26 23V31M16 7C23 7 23 17 16 17 9 17 9 7 16 7M16 0C0 0 0 0 0 16 0 32 0 32 16 32 32 32 32 32 32 16 32 0 31.992 0 16 0L16 1C31 1 31 1 31 16 31 31 31 31 16 31 1 31 1 31 1 16 1 1 1 1 16 1");
        profileIcon.setFill(this.primaryColor);

        Label nameLabel = new Label(text);
        nameLabel.setTextFill(onSurfaceColor);
        nameLabel.setFont(Font.font("Inter Regular", 14));

        item.getChildren().addAll(profileIcon, nameLabel);

        // Material Design state layer
        item.setOnMouseEntered(e -> item.setStyle("-fx-background-color:" + toCssCode(surfaceColor.brighter()) +";"));
        item.setOnMouseExited(e -> item.setStyle("-fx-background-color: transparent;"));

        // Item click handling moved to controller via callback
        item.setOnMouseClicked(e -> {
            if (callback != null) {
                callback.onMenuItemSelected(id);
                popup.hide();
            }
        });
        menuBox.getChildren().add(item);
    }

    public void addSeparator() {
        Region separator = new Region();
        separator.setPrefHeight(1);
        separator.setBackground(new Background(new BackgroundFill(
                outlineColor, CornerRadii.EMPTY, Insets.EMPTY)));
        separator.setMaxWidth(Double.MAX_VALUE);

        menuBox.getChildren().add(separator);
    }

    public void addProfileItem(String name, Image img, String id) {
        HBox item = new HBox(8);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(8, 8, 8, 16));
        item.setId(id);

        // Material Design avatar
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(32);
        imgView.setFitHeight(32);
        Rectangle clip = new Rectangle(32, 32);
        clip.setArcWidth(16);
        clip.setArcHeight(16);
        imgView.setClip(clip);
        Label nameLabel = new Label(name);
        nameLabel.setTextFill(onSurfaceColor);
        nameLabel.setFont(Font.font("Inter Regular", 14));

        item.getChildren().addAll(imgView, nameLabel);

        // Material Design state layer
        item.setOnMouseEntered(e -> item.setStyle("-fx-background-color:" + toCssCode(surfaceColor.brighter()) +"; "));
        item.setOnMouseExited(e -> item.setStyle("-fx-background-color: transparent;"));

        // Item click handling moved to controller via callback
        item.setOnMouseClicked(e -> {
            if (callback != null) {
                callback.onMenuItemSelected(id);
                popup.hide();
            }
        });
        menuBox.getChildren().add(item);
    }

    public void addFooterItem(String text, Runnable action) {
        HBox buttonContainer = new HBox();
        buttonContainer.setPadding(new Insets(2,16,2,16));
        buttonContainer.setAlignment(Pos.CENTER);
        Button actionBtn = new Button(text);
        actionBtn.setMaxWidth(Double.MAX_VALUE);
        actionBtn.setStyle(
                "-fx-background-color:" + toCssCode(surfaceColor) +"; " +
                        "-fx-text-fill:" + toCssCode(onSurfaceColor) +";"+
                        "-fx-background-radius: 24px; " +
                        "-fx-font-size: 1.3em; " +
                        "-fx-font-weight: bold;"
        );
        actionBtn.setAlignment(Pos.CENTER);
        actionBtn.setOnMouseEntered(e ->
                actionBtn.setStyle(
                        "-fx-background-color: " + toCssCode(surfaceColor.brighter()) +"; " +
                                "-fx-text-fill:" + toCssCode(onSurfaceColor) +"; " +
                                "-fx-background-radius: 24px; " +
                                "-fx-font-size: 1.3em; " +
                                "-fx-font-weight: bold;"
                )
        );

        actionBtn.setOnMouseExited(e ->
                actionBtn.setStyle(
                        "-fx-background-color: " + toCssCode(surfaceColor) +"; " +
                                "-fx-text-fill:" + toCssCode(onSurfaceColor) +"; " +
                                "-fx-background-radius: 24px; " +
                                "-fx-font-size: 1.3em; " +
                                "-fx-font-weight: bold;"
                )
        );

        actionBtn.setOnAction(e -> {
            if (action != null) {
                action.run();
            }
            popup.hide();
        });

        buttonContainer.getChildren().add(actionBtn);
        HBox.setHgrow(actionBtn, Priority.ALWAYS);

        menuBox.getChildren().add(buttonContainer);
    }

    public void show(Node anchor) {
        if (anchor == null || anchor.getScene() == null) {
            return;
        }
        double anchorX = anchor.localToScreen(-width+anchor.getLayoutBounds().getWidth(), 0).getX();//menuboxwidth + la larghezza dell'imageview
        double anchorY = anchor.localToScreen(0, 0).getY();
        double posY = anchorY + anchor.getBoundsInLocal().getHeight();
        popup.show(anchor.getScene().getWindow(), anchorX, posY);
    }
    private static String toCssCode(Color color) {
        return "rgba("+ (color.getRed() * 255)+", "+ (color.getGreen() * 255)+", "+ (color.getBlue() * 255)+", "+ color.getOpacity() +" )";
    }

}