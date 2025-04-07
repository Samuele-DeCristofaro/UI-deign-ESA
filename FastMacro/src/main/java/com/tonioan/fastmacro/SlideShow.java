package com.tonioan.fastmacro;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import java.util.List;
import java.util.Objects;
import javafx.scene.shape.SVGPath;

public class SlideShow extends VBox {

    private int cardsCount = 16;
    private int padding = 15;
    private int cardsWidth = 200;
    private ScrollPane scrollPane;
    private HBox CardsContainer;
    private Timeline scrollAnimation;
    private Button leftButton;
    private Button rightButton;
    private String color;

    public SlideShow(HelloController c, String category, List<FilmCard> filmCards, boolean isTitled, int card_width, String _color) {
        color=_color;
        // Load CSS
        this.getStyleClass().add("slide-show");
        this.setStyle("-fx-background-color:"+ color +";"); // =togliere se il background e transparent (color deve rimandere per la sfumaura
        cardsCount = filmCards.size();
        cardsWidth = card_width;
        // Create title
        if (isTitled) {
            TitleBox titleBox = new TitleBox(category,true,true,40,15,"transparent","eee");
            titleBox.getArrowButton().setOnAction(event -> {
                c.categoryButtonClicked();
            });
            this.getChildren().add(titleBox);
        }
        // Create slider with images
        StackPane sliderContainer = createSlider(filmCards,c);
        this.getChildren().add(sliderContainer);
        this.setMaxWidth(1920);//solo debug
    }



    /**
     * Creates the slider container with navigation buttons
     */
    private StackPane createSlider(List<FilmCard> filmCards, HelloController c) {
        // Create ScrollPane with image container
        scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-pane");
        CardsContainer = new HBox(padding); // 10 space between images
        CardsContainer.getStyleClass().add("cards-container");
        CardsContainer.setAlignment(Pos.CENTER_LEFT);


        for (FilmCard x : filmCards) {
            CardsContainer.getChildren().add(x);
        }

        // Configure ScrollPane
        scrollPane.setContent(CardsContainer);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
        scrollPane.setFitToHeight(true);

        // Create navigation buttons
        leftButton = createButton(true);
        rightButton = createButton(false);

        // Create StackPane container to overlap buttons over ScrollPane
        StackPane sliderContainer = new StackPane();
        sliderContainer.getChildren().addAll(scrollPane, leftButton, rightButton);
        StackPane.setAlignment(leftButton, Pos.CENTER_LEFT);
        StackPane.setAlignment(rightButton, Pos.CENTER_RIGHT);

        // Add listener to check when ScrollPane is at edges
        scrollPane.hvalueProperty().addListener((observable, oldValue, newValue) ->
                updateButtonVisibility(newValue.doubleValue()));

        // Initialize button visibility
        updateButtonVisibility(0);

        return sliderContainer;
    }

    /**
     * Updates button visibility based on scroll position
     */
    private void updateButtonVisibility(double scrollValue) {
        // If at the beginning, hide left button
        if (scrollValue <= 0.001) {
            hideButton(leftButton, true);
        } else {
            showButton(leftButton, true);
        }

        // If at the end, hide right button
        if (scrollValue >= 0.999) {
            hideButton(rightButton, false);
        } else {
            showButton(rightButton, false);
        }
    }

    /**
     * Shows a button with animation
     */
    private void showButton(Button button, boolean isLeft) {
        if (button.getOpacity() < 1) {
            FadeTransition fadeIn = new FadeTransition(Duration.millis(200), button);
            fadeIn.setFromValue(button.getOpacity());
            fadeIn.setToValue(1.0);

            TranslateTransition slideIn = new TranslateTransition(Duration.millis(200), button);
            slideIn.setFromX(isLeft ? -button.getWidth() : button.getWidth());
            slideIn.setToX(0);

            fadeIn.play();
            slideIn.play();
        }
    }

    /**
     * Hides a button with animation
     */
    private void hideButton(Button button, boolean isLeft) {
        if (button.getOpacity() > 0) {
            FadeTransition fadeOut = new FadeTransition(Duration.millis(200), button);
            fadeOut.setFromValue(button.getOpacity());
            fadeOut.setToValue(0.0);

            TranslateTransition slideOut = new TranslateTransition(Duration.millis(200), button);
            slideOut.setFromX(0);
            slideOut.setToX(isLeft ? -button.getWidth() : button.getWidth());

            fadeOut.play();
            slideOut.play();
        }
    }

    /**
     * Creates a navigation button with icon
     */
    private Button createButton(boolean isLeft) {
        Button button = new Button();
       // se decidiamo che gli scroolopanel hanno un border -fx-background-radius: 0 0 0 20; -fx-background-radius: 0 0 20 0;
        button.getStyleClass().add("nav-button");
        String lx_style = "-fx-background-radius: 0 0 0 20; -fx-background-color: linear-gradient(to right, "+color+",transparent); -fx-pref-width:"+(int)(cardsWidth/7) +";";
        String rx_style ="-fx-background-radius: 0 0 20 0; -fx-background-color: linear-gradient(to left, "+color+", transparent); -fx-pref-width:"+(int)(cardsWidth/7) +";";
        button.setStyle(isLeft ? lx_style : rx_style);

        SVGPath svgPath = new SVGPath();
        svgPath.setContent("M 504 -480 L 320 -664 l 56 -56 240 240 -240 240 -56 -56 184 -184 Z");
        svgPath.setFill(Color.rgb(240, 240, 240));
        double scaleFactor = 0.1;
        Scale scale = new Scale(scaleFactor, scaleFactor);

        if (isLeft) {
            scale.setX(-scaleFactor);
        }
        svgPath.getTransforms().add(scale);

        button.setGraphic(svgPath);

        // Add button action
        if (isLeft) {
            button.setOnAction(event -> {
                // Calculate position of previous item
                double visibleItems = calculateVisibleItems();
                double scrollAmount = 1.0 /(cardsCount - visibleItems)+1/ (2.5*(cardsCount+padding*cardsCount - visibleItems));//////////////////
                double targetValue = Math.max(0, scrollPane.getHvalue() - scrollAmount);
                animateScroll(targetValue);
            });
        } else {
            button.setOnAction(event -> {
                // Calculate position of next item
                double visibleItems = calculateVisibleItems();

                double scrollAmount = 1.0 /(cardsCount - visibleItems)+1/ (2.5*(cardsCount+padding*cardsCount - visibleItems));///////////////
                double targetValue = Math.min(1.0, scrollPane.getHvalue() + scrollAmount);
                animateScroll(targetValue);
            });
        }
        button.prefWidth((double) cardsWidth /6);
        return button;
    }

    /**
     * Calculates how many items are visible in the viewport
     */
    private  double calculateVisibleItems() {
        // Calculate how many images are visible in the viewport
        double viewportWidth = scrollPane.getViewportBounds().getWidth();
        double itemWidth = (double)cardsWidth +padding; // image width + space
        return viewportWidth / itemWidth;
    }

    /**
     * Animates the scrolling of ScrollPane
     */
    private void animateScroll(double targetValue) {
        // If there's an animation in progress, stop it
        if (scrollAnimation != null && scrollAnimation.getStatus() == Timeline.Status.RUNNING) {
            scrollAnimation.stop();
        }

        // Create new animation with interpolator for smooth movement
        scrollAnimation = new Timeline();

        // Add initial and final keyframe for smooth animation
        KeyValue keyValue = new KeyValue(
                scrollPane.hvalueProperty(),
                targetValue,
                Interpolator.SPLINE(0.4, 0.0, 0.2, 1.0) // Easing curve for natural movement
        );

        KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
        scrollAnimation.getKeyFrames().add(keyFrame);

        // Start animation
        scrollAnimation.play();
    }
}