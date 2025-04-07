// ScrollView.java
package com.tonioan.fastmacro;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.List;

public class ScrollView extends Control {
    // Constants
    private static final String DEFAULT_STYLE_CLASS = "scroll-view";
    private static final String DEFAULT_ARROW_ICON = "M 504 -480 L 320 -664 l 56 -56 240 240 -240 240 -56 -56 184 -184 Z";

    // Properties
    private final StringProperty title = new SimpleStringProperty(this, "title", "title");
    private final StringProperty buttonText = new SimpleStringProperty(this, "buttonText", "Watch more");
    private final StringProperty arrowIcon = new SimpleStringProperty(this, "arrowIcon", DEFAULT_ARROW_ICON);
    private final ObjectProperty<Color> backgroundColor = new SimpleObjectProperty<>(this, "backgroundColor", Color.GRAY);
    private final ObjectProperty<Color> foreColor = new SimpleObjectProperty<>(this, "foreColor", Color.BLACK);
    private final IntegerProperty spacing = new SimpleIntegerProperty(this, "spacing", 0);
    private final ObservableList<Pane> items = FXCollections.observableArrayList();
    // Constructor
    public ScrollView() {
        this("");
    }

    public ScrollView(String title) {
        this.title.set(title);
        initialize();
    }

    private void initialize() {
        getStyleClass().add(DEFAULT_STYLE_CLASS);
        new ScrollViewSkin(this,foreColor.get());
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ScrollViewSkin(this,foreColor.get());
    }

    // Public API methods
    public void setContent(List<Pane> objects) {
        items.clear();
        items.addAll(objects);
    }

    public void addItem(Pane item) {
        items.add(item);
    }

    public void removeItem(Pane item) {
        items.remove(item);
    }

    // Getters and Setters for properties
    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getButtonText() {
        return buttonText.get();
    }

    public StringProperty buttonTextProperty() {
        return buttonText;
    }

    public void setButtonText(String text) {
        this.buttonText.set(text);
    }

    public String getArrowIcon() {
        return arrowIcon.get();
    }

    public StringProperty arrowIconProperty() {
        return arrowIcon;
    }

    public void setArrowButtonIcon(String svg) {
        this.arrowIcon.set(svg);
    }

    public Color getBackgroundColor() {
        return backgroundColor.get();
    }

    public ObjectProperty<Color> backgroundColorProperty() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor.set(color);
    }

    public Color getForeColor() {
        return foreColor.get();
    }

    public ObjectProperty<Color> foreColorProperty() {
        return foreColor;
    }

    public void setForeColor(Color color) {
        this.foreColor.set(color);
    }

    public int getSpacing() {
        return spacing.get();
    }

    public IntegerProperty spacingProperty() {
        return spacing;
    }

    public void setSpacing(int value) {
        this.spacing.set(value);
    }

    public ObservableList<Pane> getItems() {
        return items;
    }

    private ObjectProperty<EventHandler<MouseEvent>> arrowButtonAction =
            new SimpleObjectProperty<>(this, "arrowButtonAction");

    public final ObjectProperty<EventHandler<MouseEvent>> arrowButtonActionProperty() {
        return arrowButtonAction;
    }

    public final void setArrowButtonAction(EventHandler<MouseEvent> handler) {
        arrowButtonActionProperty().set(handler);
    }

    public final EventHandler<MouseEvent> getArrowButtonAction() {
        return arrowButtonActionProperty().get();
    }
}

