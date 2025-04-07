package com.tonioan.fastmacro;


import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.ArrayList;
import java.util.List;

public class CarouselSkin extends SkinBase<Carousel> {

    private static final double CENTER_SCALE = 2;
    private static final double SIDE_SCALE = 1.2;
    private static final double CARD_OVERLAP = 40;

    private final StackPane mainContainer;
    private final HBox cardsContainer;
    private final HBox dotsContainer;
    private Timeline animationTimeline;
    private List<StackPane> cardWrappers; // Store created card wrappers
    private DoubleProperty maxCardHeight = new SimpleDoubleProperty(0);

    public CarouselSkin(Carousel control) {
        super(control);

        // Initialize card wrappers list
        cardWrappers = new ArrayList<>();

        // Container for all cards with proper spacing
        cardsContainer = new HBox();
        cardsContainer.setAlignment(Pos.CENTER);
        cardsContainer.setSpacing(-CARD_OVERLAP); // Negative spacing for overlap effect

        // Bind the height of the cards container to the max card height * CENTER_SCALE
        cardsContainer.minHeightProperty().bind(maxCardHeight.multiply(CENTER_SCALE));
        cardsContainer.prefHeightProperty().bind(maxCardHeight.multiply(CENTER_SCALE));

        // Container for navigation dots
        dotsContainer = new HBox(10);
        dotsContainer.setAlignment(Pos.CENTER);
        dotsContainer.setPadding(new Insets(10, 0, 10, 0));

        // Main layout with cards and dots
        VBox contentLayout = new VBox(20);
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.getChildren().addAll(cardsContainer, dotsContainer);

        // Main container
        mainContainer = new StackPane(contentLayout);
        getChildren().add(mainContainer);

        // Set up listeners
        control.getItems().addListener((ListChangeListener<? super Node>) (c) -> {
            System.out.println("listener1");
            Platform.runLater(this::rebuildCarousel);
        });

        control.currentIndexProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("listener");
            navigateToIndex(newVal.intValue());
        });

        // Initial setup
        rebuildCarousel();
    }

    private void rebuildCarousel() {
        // Clear everything
        cardsContainer.getChildren().clear();
        dotsContainer.getChildren().clear();
        cardWrappers.clear();

        Carousel carousel = getSkinnable();
        int itemCount = carousel.getItems().size();

        if (itemCount == 0) return;

        // Create card wrappers for each item but don't add them to the container yet
        for (int i = 0; i < itemCount; i++) {
            Node item = carousel.getItems().get(i);
            StackPane cardWrapper = createCardWrapper(item, i);
            cardWrappers.add(cardWrapper);
        }

        // Create navigation dots
        for (int i = 0; i < itemCount; i++) {
            Button dot = createDotButton(i);
            dotsContainer.getChildren().add(dot);
        }

        // Update the max card height
        updateMaxCardHeight();

        // Initial layout
        Platform.runLater(() -> navigateToIndex(carousel.getCurrentIndex()));
    }

    private void updateMaxCardHeight() {
        // Get the maximum height of all cards
        double maxHeight = 0;
        for (StackPane cardWrapper : cardWrappers) {
            // Ensure the card has a preferred height
            cardWrapper.applyCss();
            cardWrapper.layout();

            double cardHeight = cardWrapper.prefHeight(-1);
            if (cardHeight > maxHeight) {
                maxHeight = cardHeight;
            }
        }

        // Update the max card height property
        maxCardHeight.set(maxHeight);

        // Set up listeners for each card to update the max height if they change
        for (StackPane cardWrapper : cardWrappers) {
            cardWrapper.heightProperty().addListener((obs, oldVal, newVal) -> {
                updateMaxCardHeight();
            });
        }
    }

    private StackPane createCardWrapper(Node content, int index) {
        StackPane cardWrapper = new StackPane();
        cardWrapper.getStyleClass().add("carousel-card");

        // Add drop shadow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        dropShadow.setRadius(10);
        cardWrapper.setEffect(dropShadow);

        // Content container
        StackPane contentContainer = new StackPane(content);
        contentContainer.setAlignment(Pos.CENTER);
        cardWrapper.getChildren().add(contentContainer);

        // Make card clickable to navigate
        cardWrapper.setOnMouseClicked(e -> {
            try {
                getSkinnable().goTo(index);
            } catch (Exception ex) {
                System.err.println("Error navigating to card: " + ex.getMessage());
            }
        });

        return cardWrapper;
    }

    private Button createDotButton(int index) {
        Button dot = new Button();
        dot.getStyleClass().add("carousel-dot");
        dot.setMinSize(12, 12);
        dot.setPrefSize(12, 12);
        dot.setMaxSize(12, 12);
        dot.setStyle("-fx-background-radius: 6; -fx-background-color: #CCCCCC;");

        // Set active state for current index
        if (index == getSkinnable().getCurrentIndex()) {
            dot.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), true);
            dot.setStyle("-fx-background-radius: 6; -fx-background-color: #FFFFFF;");
        }

        // Handle click action
        dot.setOnAction(e -> {
            try {
                getSkinnable().goTo(index);
            } catch (Exception ex) {
                System.err.println("Error when clicking dot: " + ex.getMessage());
            }
        });

        return dot;
    }

    private void navigateToIndex(int targetIndex) {
        int itemCount = getSkinnable().getItems().size();
        if (itemCount == 0) return;

        int normalizedIndex = ((targetIndex % itemCount) + itemCount) % itemCount;
        int prevIndex = (normalizedIndex - 1 + itemCount) % itemCount;
        int nextIndex = (normalizedIndex + 1) % itemCount;

        // Clear the cards container first
        cardsContainer.getChildren().clear();

        // Add only the cards we want to display (previous, current, next)
        // You can adjust this logic based on how many cards you want visible at once
        if (itemCount > 2) {
            // Add previous card
            cardsContainer.getChildren().add(cardWrappers.get(prevIndex));
        }

        // Add current card
        cardsContainer.getChildren().add(cardWrappers.get(normalizedIndex));

        if (itemCount > 1) {
            // Add next card
            cardsContainer.getChildren().add(cardWrappers.get(nextIndex));
        }

        // Apply appropriate scaling to the cards
        for (int i = 0; i < cardsContainer.getChildren().size(); i++) {
            StackPane card = (StackPane) cardsContainer.getChildren().get(i);
            // Center card gets larger scale
            if (i == (cardsContainer.getChildren().size() > 2 ? 1 : 0)) {
                card.setScaleX(CENTER_SCALE);
                card.setScaleY(CENTER_SCALE);
                card.setStyle("-fx-view-order: 0;");
            } else {
                card.setScaleX(SIDE_SCALE);
                card.setScaleY(SIDE_SCALE);
                card.setStyle("-fx-view-order: 1;");
            }
        }

        // Update dots
        for (int i = 0; i < dotsContainer.getChildren().size(); i++) {
            Button dot = (Button) dotsContainer.getChildren().get(i);
            boolean isActive = (i == normalizedIndex);
            dot.pseudoClassStateChanged(PseudoClass.getPseudoClass("active"), isActive);
            dot.setStyle("-fx-background-radius: 6; -fx-background-color: " +
                    (isActive ? "#FFFFFF" : "#CCCCCC") + ";");
        }

        // Update max height after navigation is complete
        Platform.runLater(this::updateMaxCardHeight);
    }
}