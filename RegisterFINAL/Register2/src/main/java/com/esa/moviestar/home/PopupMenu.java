package com.esa.moviestar.home;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.util.ResourceBundle;

public class PopupMenu {

    private final Popup popup;
    private final VBox menuBox;
    private MenuCallback callback;
    private final ResourceBundle resourceBundle =ResourceBundle.getBundle("com.esa.moviestar.images.svg-paths.general-svg");
    private final double width = 256;
    public interface MenuCallback {
        void onMenuItemSelected(String item);
    }

    public PopupMenu() {
        this.popup = new Popup();

        // Create menu content container
        this.menuBox = new VBox(4);
        menuBox.setPadding(new Insets(16, 0, 0, 0));
        menuBox.setMaxWidth(width);
        menuBox.setMinWidth(width);

        // Material Design elevation with shadow
        DropShadow shadow = new DropShadow();
        shadow.setRadius(10.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        menuBox.getStyleClass().addAll("surface-opaque", "medium-item","surface-dim-border");
        menuBox.setEffect(shadow);

        // Create container
        StackPane container = new StackPane(menuBox);

        popup.getContent().add(container);
        popup.setAutoHide(true);
    }

    public void setMenuCallback(MenuCallback callback) {
        this.callback = callback;
    }

    public void addHeaderItem(String txt, String id) {
        HBox item = new HBox();
        item.setMinHeight(40.0);

        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add("chips");
        item.setId(id);

        SVGPath profileIcon = new SVGPath();
        profileIcon.setContent(resourceBundle.getString("user"));
        profileIcon.getStyleClass().add("on-primary");

        Text text = new Text(txt);
        text.getStyleClass().addAll("medium-text","on-primary");

        item.getChildren().addAll(profileIcon, text);

        // Material Design state layer
        item.setOnMouseEntered(e -> item.getStyleClass().add("surface-dim"));
        item.setOnMouseExited(e -> item.getStyleClass().remove("surface-dim"));

        // Item click handling moved to controller via callback
        item.setOnMouseClicked(e -> {
            if (callback != null) {
                callback.onMenuItemSelected(id);
                popup.hide();
            }
        });
        menuBox.getChildren().add(item);

    }

    public void addProfileItem(String name, Image img, String id) {
        HBox item = new HBox();
        item.setMinHeight(40.0);
        item.setAlignment(Pos.CENTER_LEFT);
        item.getStyleClass().add("chips");
        item.setId(id);

        // Material Design avatar
        ImageView imgView = new ImageView(img);
        imgView.setFitWidth(32);
        imgView.setFitHeight(32);
        Rectangle clip = new Rectangle(32, 32);
        clip.setArcWidth(16);
        clip.setArcHeight(16);
        imgView.setClip(clip);
        Text text = new Text(name);
        text.getStyleClass().addAll("medium-text","on-primary");

        item.getChildren().addAll(imgView, text);

        // Material Design state layer
        item.setOnMouseEntered(e -> item.getStyleClass().add("surface-dim"));
        item.setOnMouseExited(e -> item.getStyleClass().remove("surface-dim"));

        // Item click handling moved to controller via callback
        item.setOnMouseClicked(e -> {
            if (callback != null) {
                callback.onMenuItemSelected(id);
                popup.hide();
            }
        });
        menuBox.getChildren().add(item);
    }

    public void addFooterItem(String txt, Runnable action) {
        HBox button = new HBox();
        button.setMinHeight(40.0);
        button.setAlignment(Pos.CENTER);
        SVGPath profileIcon = new SVGPath();
        profileIcon.setContent(resourceBundle.getString("email"));
        profileIcon.getStyleClass().add("on-primary");

        Text text = new Text(txt);
        text.getStyleClass().addAll("medium-text","on-primary");

        button.getStyleClass().addAll("small-item","surface-dim-border","chips","surface-transparent");
        button.setOnMouseEntered(e -> {button.getStyleClass().remove("surface-transparent");button.getStyleClass().add("surface-dim");});

        button.setOnMouseExited(e ->{button.getStyleClass().remove("surface-dim");button.getStyleClass().remove("surface-transparent");});

        button.setOnMouseClicked(e -> {
            if (action != null) {
                action.run();
            }
            popup.hide();
        });

        button.getChildren().addAll(profileIcon,text);

        menuBox.getChildren().add(button);
        VBox.setMargin(button, new Insets(6));
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