package com.tonioan.fastmacro;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CustomMenu extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Button triggerButton = new Button("Show Menu");

        // Material Design button styling
        triggerButton.setStyle(
                "-fx-background-color: #6750A4; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 20; " +
                        "-fx-padding: 10 20 10 20; " +
                        "-fx-font-size: 14px;"
        );

        triggerButton.setOnAction(e -> {
            MaterialPopupMenu menu = new MaterialPopupMenu(primaryStage, triggerButton,
                    item -> System.out.println("Selected: " + item));
            menu.show(triggerButton.localToScreen(0, triggerButton.getHeight()));
        });

        StackPane container = new StackPane(triggerButton);
        container.setPadding(new Insets(50));
        container.setStyle("-fx-background-color: #FFFBFE;");
        root.setCenter(container);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Material Design Menu Demo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class MaterialPopupMenu {
    private final Popup popup;
    private final MenuCallback callback;
    private final Stage owner;

    // Material Colors
    private final Color surfaceColor = Color.rgb(255, 251, 254);
    private final Color onSurfaceColor = Color.rgb(28, 27, 31);
    private final Color primaryColor = Color.rgb(103, 80, 164);
    private final Color outlineColor = Color.rgb(121, 116, 126, 0.3);

    public interface MenuCallback {
        void onMenuItemSelected(String item);
    }

    public MaterialPopupMenu(Stage owner, Button anchor, MenuCallback callback) {
        this.popup = new Popup();
        this.callback = callback;
        this.owner = owner;

        VBox menuBox = createMenuContent();

        // Material Design elevation with shadow
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));

        menuBox.setBackground(new Background(new BackgroundFill(
                surfaceColor, new CornerRadii(12), Insets.EMPTY)));
        menuBox.setBorder(new Border(new BorderStroke(
                outlineColor, BorderStrokeStyle.SOLID, new CornerRadii(12), new BorderWidths(1))));
        menuBox.setEffect(shadow);

        // Create container
        StackPane container = new StackPane(menuBox);

        popup.getContent().add(container);
        popup.setAutoHide(true);

        // Close popup when clicking elsewhere
        owner.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) popup.hide();
        });
    }

    private VBox createMenuContent() {
        VBox menuBox = new VBox(4);
        menuBox.setPadding(new Insets(8, 0, 8, 0));
        menuBox.setMaxWidth(280);
        menuBox.setMinWidth(280);

        // Settings header
        HBox settingsHeader = createMenuItem("Settings", "settings");

        // Profile items
        HBox profileGiacomino = createProfileItem("Giacomino", Color.rgb(103, 169, 74));
        HBox profileMarco = createProfileItem("Marco", Color.rgb(242, 153, 40));
        HBox profileKayla = createProfileItem("Kayla", Color.rgb(142, 68, 173));

        // Separator
        Region separator1 = new Region();
        separator1.setPrefHeight(1);
        separator1.setBackground(new Background(new BackgroundFill(
                outlineColor, CornerRadii.EMPTY, Insets.EMPTY)));
        separator1.setMaxWidth(Double.MAX_VALUE);
        separator1.setPadding(new Insets(4, 0, 4, 0));

        Region separator2 = new Region();
        separator2.setPrefHeight(1);
        separator2.setBackground(new Background(new BackgroundFill(
                outlineColor, CornerRadii.EMPTY, Insets.EMPTY)));
        separator2.setMaxWidth(Double.MAX_VALUE);
        separator2.setPadding(new Insets(4, 0, 4, 0));

        // Email change button - Material Design button
        HBox buttonContainer = new HBox();
        buttonContainer.setPadding(new Insets(4, 16, 4, 16));
        buttonContainer.setAlignment(Pos.CENTER);

        Button changeEmailBtn = new Button("Change Email");
        changeEmailBtn.setStyle(
                "-fx-background-color: #E8DEF8; " +
                        "-fx-text-fill: #6750A4; " +
                        "-fx-background-radius: 100; " +
                        "-fx-padding: 10 16 10 16; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold;"
        );
        changeEmailBtn.setAlignment(Pos.CENTER);
        changeEmailBtn.setOnMouseEntered(e ->
                changeEmailBtn.setStyle(
                        "-fx-background-color: #D9CFED; " +
                                "-fx-text-fill: #6750A4; " +
                                "-fx-background-radius: 100; " +
                                "-fx-padding: 10 16 10 16; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold;"
                )
        );

        changeEmailBtn.setOnMouseExited(e ->
                changeEmailBtn.setStyle(
                        "-fx-background-color: #E8DEF8; " +
                                "-fx-text-fill: #6750A4; " +
                                "-fx-background-radius: 100; " +
                                "-fx-padding: 10 16 10 16; " +
                                "-fx-font-size: 14px; " +
                                "-fx-font-weight: bold;"
                )
        );

        changeEmailBtn.setOnAction(e -> {
            callback.onMenuItemSelected("change_email");
            popup.hide();
        });

        buttonContainer.getChildren().add(changeEmailBtn);
        HBox.setHgrow(changeEmailBtn, Priority.ALWAYS);

        // Add all elements to menu
        menuBox.getChildren().addAll(
                settingsHeader,
                separator1,
                profileGiacomino,
                profileMarco,
                profileKayla,
                separator2,
                buttonContainer);

        return menuBox;
    }

    private HBox createMenuItem(String text, String id) {
        HBox item = new HBox(16);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(12, 16, 12, 16));

        Label label = new Label(text);
        label.setTextFill(onSurfaceColor);
        label.setFont(Font.font("System", FontWeight.BOLD, 16));

        // Material Design icon placeholder
        Circle icon = new Circle(12);
        icon.setFill(Color.rgb(103, 80, 164, 0.8));

        item.getChildren().addAll(icon, label);

        // Material Design ripple effect simulation
        Rectangle rippleRect = new Rectangle();
        rippleRect.setWidth(item.getWidth());
        rippleRect.setHeight(item.getHeight());
        rippleRect.setFill(Color.TRANSPARENT);

        item.setOnMouseClicked(e -> {
            callback.onMenuItemSelected(id);
            popup.hide();
        });

        // Material Design state layer for hover
        item.setOnMouseEntered(e -> {
            item.setStyle("-fx-background-color: rgba(103, 80, 164, 0.08); -fx-cursor: hand;");
        });
        item.setOnMouseExited(e -> {
            item.setStyle("-fx-background-color: transparent;");
        });

        return item;
    }

    private HBox createProfileItem(String name, Color color) {
        HBox item = new HBox(16);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(12, 16, 12, 16));

        // Material Design avatar
        Circle profileCircle = new Circle(16);
        profileCircle.setFill(color);

        Label nameLabel = new Label(name);
        nameLabel.setTextFill(onSurfaceColor);
        nameLabel.setFont(Font.font("System", 14));

        item.getChildren().addAll(profileCircle, nameLabel);

        String profileId = "profile_" + name.toLowerCase();
        item.setOnMouseClicked(e -> {
            callback.onMenuItemSelected(profileId);
            popup.hide();
        });

        // Material Design state layer
        item.setOnMouseEntered(e -> {
            item.setStyle("-fx-background-color: rgba(103, 80, 164, 0.08); -fx-cursor: hand;");
        });
        item.setOnMouseExited(e -> {
            item.setStyle("-fx-background-color: transparent;");
        });

        return item;
    }

    public void show(javafx.geometry.Point2D point) {
        // Calculate position to show popup centered below the anchor
        popup.show(owner, point.getX(), point.getY());
    }
}